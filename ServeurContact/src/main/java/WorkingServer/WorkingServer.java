package WorkingServer;

import Crypto.AES.AES;
import Crypto.DES.DES;
import Crypto.Digest.Digest;
import DTO.*;

import JDBCAccess.BeanBdPersonnel;
import MySqlAccess.MysqlAccess;

import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class WorkingServer implements Runnable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String nomDeCompte;
    private BeanBdPersonnel beanBdPersonnel;
    private SecretKey ks;
    SecretKey kcs;
    public WorkingServer(Socket s,
                         SecretKey ks,
                         BeanBdPersonnel beanBdPersonnel) throws IOException
    {
        this.socket =s;
        this.beanBdPersonnel = beanBdPersonnel;
        this.ks = ks;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        nomDeCompte = null;
        kcs = null;
    }

    @Override
    public void run() {
        boolean clientConnected = false;
        try{
            System.out.println("AVANT AUTHENTIFICATE");
            kcs = authentificate(in,out,ks);
            System.out.println("KCS != null ? " + kcs!=null);
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
        System.out.println("clientConnected:"+clientConnected);
        ArrayList<Serializable> lReponse = new ArrayList<>();
        while(clientConnected) {
            try {
                String categoryReceived = receiveCategoryAsked(in);
                lReponse = new ArrayList<>();
                List<String> emailPersonnel = beanBdPersonnel.getEmailPersonnelOnCategorie(MysqlAccess.getConnection(),CategoryEnum.valueOf(categoryReceived).getValue());
                lReponse.add(new Erreur("Erreur",Erreur.SUCCESS));
                for(String email : emailPersonnel)
                {
                    lReponse.add(DES.encrypt(email,kcs));
                }

            }
            catch(SocketException se)
            {
                log("SOCKET EXCEPTION " +se.getMessage());
                lReponse.add(new Erreur("Erreur", Erreur.ERREUR));
                clientConnected=false;
            }
            catch (Exception e) {
                log(e.getMessage());
                lReponse.add(new Erreur("Erreur", Erreur.ERREUR));
            }
            try {
                if(clientConnected)
                    sendObject(out,lReponse);
            }
            catch(SocketException e)
            {
                e.printStackTrace();
                clientConnected=false;
            }catch (IOException e) {
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

    private String receiveCategoryAsked(ObjectInputStream in) throws IllegalBlockSizeException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, NoSuchProviderException, ClassNotFoundException {
        SealedObject sealedObject = (SealedObject) in.readObject();
        return (String) DES.decrypt(sealedObject,kcs);
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
            System.out.println("Tcs server name : " + tcs.getNomServeur());
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
            lReponse.add(new Erreur("Une exception s'est produite : " +e.getMessage(), Erreur.ERREUR));
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
    }




