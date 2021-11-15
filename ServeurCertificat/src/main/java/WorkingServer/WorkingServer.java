package WorkingServer;

import ConfigurationFileReader.PropertyLoader;
import Crypto.AES.AES;
import Crypto.DES.DES;
import Crypto.Digest.Digest;
import DTO.*;
import GeneratorPDFFile.GeneratorPDFFile;

import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CryptoPrimitive;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class WorkingServer implements Runnable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String nomDeCompte;
    private SecretKey ks;
    private GeneratorPDFFile generatorPDFFile;
    SecretKey kcs;

    public WorkingServer(Socket s,
                         SecretKey ks) throws IOException
    {
        this.socket =s;
        this.ks = ks;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        nomDeCompte = null;
        kcs = null;
    }

    @Override
    public void run() {
        Properties prop = null;
        prop = PropertyLoader.load("ServeurCertificat\\prop.txt");

        boolean clientConnected = false;
        generatorPDFFile = new GeneratorPDFFile();
        try{
            System.out.println("AVANT AUTHENTIFICATE");
            kcs = authentificate(in,out,ks);

            if(kcs!= null) {
                System.out.println("KCS N EST PAS NUL");
                clientConnected = true;
            }
            else {
                System.out.println("KCS EST NUL");
            }
            System.out.println("APRES LE TEST ");
        }
        catch(Exception e)
        {
            log(e.getMessage());
        }
        System.out.println("ON EST LA");

        ArrayList<Serializable> lReponse = new ArrayList<>();
        while(clientConnected) {
            try {
                System.out.println("ON RECOIT LA DEMANDE DE CERTIFICAT");

                Article lReception = (Article)DES.decrypt((SealedObject)receiveObject(in),kcs);

                Article lArticle = lReception ;
                lReponse = new ArrayList<>();

                // On check si le certificat existe déjà
                System.out.println("AVANT");
                if(!generatorPDFFile.searchToCert(lArticle)){
                    System.out.println("On n'a pas le file");
                    // Le certificat n'existe pas => on le génère
                    generatorPDFFile.generatedPDFFile(lArticle);
                    System.out.println("Création d'un certificat");
                }
                System.out.println("On envoie");
                //On envoie le fichier
                //sendFile(pathCache+lArticle.getNom()+".pdf");
                byte[] fileSerialized = getFileByteArray(prop.getProperty("pathCache")+lArticle.getNom()+".pdf");
                lReponse.add(new Erreur("Erreur",Erreur.SUCCESS));
                lReponse.add(DES.encrypt((Serializable)fileSerialized,kcs));

            }
            catch(SocketException se)
            {
                log("SOCKET EXCEPTION " +se.getMessage());
                lReponse.add(new Erreur("Erreur", Erreur.ERREUR));
                clientConnected=false;
            }
            catch (Exception e) {
                log("Exception : "+e.getMessage());
                lReponse.add(new Erreur("Erreur", Erreur.ERREUR));
            }
            try {
                System.out.println("LReponse length = " + lReponse.size());
                if(clientConnected)
                    sendObject(out,lReponse);
            }catch(SocketException e)
            {
                e.printStackTrace();
                clientConnected=false;
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        try{
            this.socket.close();
        }catch (Exception e)
        {
            log(e.getMessage());
        }
    }

    private Object receiveObject(ObjectInputStream in) throws ClassNotFoundException, IOException
    {
        return in.readObject();
    }

    private void sendObject(ObjectOutputStream out, Object obj) throws IOException
    {
        out.writeObject(obj);
    }

    private void log(String message)
    {
        if(nomDeCompte!=null)
            System.out.print(nomDeCompte+")");
        System.out.println(message);
    }


    private SecretKey authentificate(ObjectInputStream in, ObjectOutputStream out, SecretKey Ks) throws IOException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        SecretKey kcsTmp = null;
        List<Serializable> lReponse = new ArrayList<>();
        try {
            /// Réception A'cs et Tcs
            /// A'cs = {nc, t, ck) Kcs
            /// Tcs = s, {nc, a, t'v, Kcs) Ks
            List<Serializable> receivedList = (List<Serializable>) in.readObject();

            ///Test du token
            ///On déchiffre le Tcs avec la Ks
            TicketGrantingService tcs = (TicketGrantingService) receivedList.get(1);
            TicketGrantingServiceCrypted tcsCrypter = (TicketGrantingServiceCrypted) AES.decrypt(tcs.getTicketGrantingServiceCrypted(), Ks);

            if(!timestampIsValid(tcsCrypter.getTimestamp(), 8))
            {
                throw new TimestampException("Invalid timestamp");
            }

            System.out.println(tcs);
            System.out.println(tcsCrypter);
            kcsTmp = tcsCrypter.getSecretKey();
            System.out.println("Kcs : " + Base64.getEncoder().encodeToString(kcsTmp.getEncoded()));
            Authentificateur auth = (Authentificateur) DES.decrypt((SealedObject) receivedList.get(0), kcsTmp);
            nomDeCompte = auth.getNom();

            long timestamp = auth.getTimpestamp();
            System.out.println("Timestamp = " + timestamp);
            byte[] checksum = Digest.hash(nomDeCompte.getBytes(StandardCharsets.UTF_8), Digest.convertLongToByteArray(timestamp), true);

            if (!Digest.compareHash(checksum, auth.getChecksum()))
                throw new ChecksumException("Checksum invalide");

            lReponse.add(new Erreur("Success", Erreur.SUCCESS));
            lReponse.add(DES.encrypt(timestamp, kcsTmp));


        } catch (Exception e) {
            log(e.getMessage());
            lReponse.add(new Erreur("Une exception s'est produite", Erreur.ERREUR));
        } finally {
            out.writeObject(lReponse);

        }
        return kcsTmp;
    }
    public static final long HOUR = 3600*1000;

    private boolean timestampIsValid(long l1, int interval)
    {
        return ((new Date().getTime()-l1)/HOUR) < interval;
    }


    private List<Serializable> encryptLArticleToSend(List<Article> lArticle) throws IllegalBlockSizeException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        List<Serializable> lSerializable = new ArrayList<>();
        lSerializable.add(new Erreur("Success", Erreur.SUCCESS));
        System.out.println("LArticle size:" + lArticle.size());
        for(Article a : lArticle)
        {
            lSerializable.add(DES.encrypt(a,kcs));
        }
        System.out.println("LSerializable size : " + lSerializable.size());
        return lSerializable;
    }
    private byte[] getFileByteArray(String filepath)
    {
        byte[] returnArray = null;
        try{
            Path path = Paths.get(filepath);
            returnArray = Files.readAllBytes(path);

        }
        catch(IOException e)
        {
            System.out.println("IOException : " + e.getMessage());
        }
        return returnArray;
    }
    private void sendFile(String path) {

        FileInputStream fis;
        BufferedInputStream bis;
        BufferedOutputStream out;
        byte[] buffer = new byte[8192];
        try {
            fis = new FileInputStream(path);
            bis = new BufferedInputStream(fis);
            out = new BufferedOutputStream(socket.getOutputStream());
            int count;
            while ((count = bis.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            out.close();
            fis.close();
            bis.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}




