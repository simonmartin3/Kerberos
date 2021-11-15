package WorkingServer;

import Crypto.AES.AES;
import Crypto.DES.DES;
import Crypto.Digest.Digest;
import DTO.*;
import JDBCAccess.BeanBdArticle;
import JDBCAccess.BeanBdCommande;
import JDBCAccess.BeanBdCommandeArticle;
import MySqlAccess.MysqlAccess;

import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
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
    private BeanBdArticle beanBdArticle;
    private BeanBdCommande beanBdCommande;
    private BeanBdCommandeArticle beanBdCommandeArticle;
    private SecretKey ks;
    SecretKey kcs;
    public WorkingServer(Socket s,
                         SecretKey ks,
                         BeanBdArticle beanBdArticle,
                         BeanBdCommande beanBdCommande,
                         BeanBdCommandeArticle beanBdCommandeArticle) throws IOException
    {
        this.socket =s;
        this.beanBdArticle = beanBdArticle;
        this.beanBdCommande = beanBdCommande;
        this.beanBdCommandeArticle = beanBdCommandeArticle;
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
                System.out.println("ON ENVOIE LA LISTE ICI LA");
                sendListArticle(out);
                List<Serializable>  lReception = (List<Serializable>)receiveObject(in);
                List<Article> lArticle = decryptListArticle(lReception);
                lReponse = new ArrayList<>();

                Connection con = MysqlAccess.getConnection();
                con.setAutoCommit(false);
                beanBdArticle.updateQuantiteArticles(con, lArticle);
                if(beanBdCommande.insertCommande(con,nomDeCompte))
                {
                    int idLastCommande = beanBdCommande.getLastId(con);
                    if(beanBdCommandeArticle.insertCommandeArticles(con,lArticle,idLastCommande))
                    {
                        con.commit();
                        lReponse.add(new Erreur("Success",Erreur.SUCCESS));
                        //lReponse.add(DES.encrypt((Serializable) lArticle,kcs));
                    }
                    else{
                        lReponse.add(new Erreur("Erreur",Erreur.ERREUR));
                    }
                }
                else{
                    lReponse.add(new Erreur("Erreur",Erreur.ERREUR));
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

    private List<Article> decryptListArticle(List<Serializable> lSerializables) throws IllegalBlockSizeException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, NoSuchProviderException, ClassNotFoundException {
        List<Article> lArticle = new ArrayList<>();
        for(Serializable s : lSerializables)
        {
            lArticle.add((Article)DES.decrypt((SealedObject) s, kcs));
        }
        return lArticle;
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
    private void sendListArticle(ObjectOutputStream out) throws IOException, SQLException, ClassNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        List<Article> lArticle = beanBdArticle.getListArticle(MysqlAccess.getConnection());
        System.out.println("ON ENVOIE LA LISTE D ARTICLE");
        List<Serializable> lArticleToSend = encryptLArticleToSend(lArticle);
        sendObject(out,lArticleToSend);

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
    }




