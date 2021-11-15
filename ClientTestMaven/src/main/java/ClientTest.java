import Crypto.AES.AES;
import Crypto.DES.DES;
import Crypto.Digest.Digest;
import Crypto.Keystore.Keystore;
import DTO.*;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ClientTest {
    public static void main(String[] args) {
        try{
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            String nomDeCompte = "client_particulier";
            long time = System.currentTimeMillis();
            Digest.setDigestAlgorithm("MD5");
            byte[] pwdHash = Digest.hash("azerty".getBytes(StandardCharsets.UTF_8),Digest.convertLongToByteArray(time),true);
            ClientRequest cr = new ClientRequest(nomDeCompte,pwdHash,time,"localhost","tgs");
            List<Serializable> lReponse = communicationWAS(cr);
            System.out.println("On est arrivé là");
            Erreur erreur = (Erreur) lReponse.get(0);
            System.out.println(erreur);
            if(erreur.getCode()==Erreur.SUCCESS)
            {
                /// KeyVersionServeur(K_ctgs,version,nom du serveur TGS) / TcTGS(K_ctgs, nom du clientRequest, limite de validité) + catégorie clientRequest
                ///List<SealedObject> => 1 = {K_ctgs, version, nom du serveur TGS}Kc(DES) / 2=>TcTgs (AES)
                ///categorie
                SecretKey Kc = Keystore.LoadFromKeyStore("D:\\Master1\\Principes de sécurité\\Kerberos\\ClientTestMaven\\src\\main\\resources\\ClientParticulierKeystore.jce","y8EsHzbs2N8WQfkK","Kc");
                KeyVersionServeur kvs = (KeyVersionServeur) AES.decrypt((SealedObject)lReponse.get(1),Kc);
                System.out.println(kvs);
                TicketGrantingService tgs = (TicketGrantingService) lReponse.get(2);
                System.out.println(tgs);
                System.out.println("Categorie : " + lReponse.get(3));


                SecretKey kctgs = kvs.getSecretKey();
                time = System.currentTimeMillis();
                byte[] checksum = Digest.hash(nomDeCompte.getBytes(StandardCharsets.UTF_8),Digest.convertLongToByteArray(time),true);
                Authentificateur auth = new Authentificateur(nomDeCompte,time,checksum);

                lReponse = communicationWTGS(auth,kctgs,tgs,"serveur_produits");
                erreur = (Erreur)lReponse.get(0);
                System.out.println(erreur);
                if(erreur.getCode()==Erreur.SUCCESS) {
                    /// KcsVersionServeur {Kcs, version, nom du serveur}Kc_tgs
                    ///TicketTCS (serveur,{Kcs, nom du client, limite validité ticket, ...} Ks)
                    KeyVersionServeur keyVersionServeur = (KeyVersionServeur) DES.decrypt((SealedObject)lReponse.get(1),kctgs);
                    System.out.println(keyVersionServeur);

                    TicketGrantingService tcs = (TicketGrantingService) lReponse.get(2);
                    System.out.println(tcs);




                    SecretKey kcs = keyVersionServeur.getSecretKey();
                    System.out.println("Kcs = " + kcs);
                    time = System.currentTimeMillis();
                    checksum = Digest.hash(nomDeCompte.getBytes(StandardCharsets.UTF_8),Digest.convertLongToByteArray(time),true);
                    auth = new Authentificateur(nomDeCompte,time,checksum);
                    Socket socket = new Socket("localhost",50003);
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    lReponse = communicationWServer(out,in,auth,kcs,tcs);
                    System.out.println("Kcs = " + Base64.getEncoder().encodeToString(kcs.getEncoded()));

                    erreur = (Erreur)DES.decrypt((SealedObject) lReponse.get(0),kcs);
                    System.out.println(erreur);
                    if(erreur.getCode()==Erreur.SUCCESS)
                    {
                        System.out.println("Kcs = " + Base64.getEncoder().encodeToString(kcs.getEncoded()));
                        Object obj = DES.decrypt((SealedObject) lReponse.get(1),kcs);
                        System.out.println(obj);
                        long timestampReceived = (long)DES.decrypt((SealedObject)lReponse.get(1),kcs);
                        if(timestampReceived == time)
                        {
                            System.out.println("TOUT EST BON");
                            List<Article> lArticleRecu = (List<Article>)in.readObject();
                            for(Article a : lArticleRecu)
                            {
                                System.out.println(a);
                            }
                        }
                    }

                    socket.close();
                }
            }
            else
            {
                System.out.println(erreur);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static List<Serializable> communicationWAS(ClientRequest cr) throws IOException, ClassNotFoundException {
        Socket socket = null;
        try {
             socket = new Socket("localhost", 50001);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(cr);
            List<Serializable> lReponse= (List<Serializable>) in.readObject();
            return lReponse;
        } catch (ClassNotFoundException exception)
        {
            throw new ClassNotFoundException(exception.getMessage());
        }
        catch(IOException ioException)
        {
            throw new IOException(ioException.getMessage());
        }
        finally
        {
            if(socket!= null)
                socket.close();
        }

    }

    private static List<Serializable> communicationWTGS(Authentificateur auth,SecretKey kctgs, TicketGrantingService ticketGrantingService,String ns) throws Exception {
        Socket socket = null;
        try{
            List<Serializable> lRequete = new ArrayList<>();
            lRequete.add(DES.encrypt(auth,kctgs));
            lRequete.add(ticketGrantingService);
            lRequete.add(ns);
            socket = new Socket("localhost", 50002);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(lRequete);
            List<Serializable> lReponse= (List<Serializable>) in.readObject();
            return lReponse;
        } catch (IllegalBlockSizeException | NoSuchPaddingException | IOException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | ClassNotFoundException e) {
            throw new Exception(e);
        } finally
        {
            if(socket!= null)
                socket.close();
        }
    }

    private static List<Serializable> communicationWServer(ObjectOutputStream out, ObjectInputStream in,Authentificateur auth, SecretKey kcs, TicketGrantingService ticketGrantingService) throws Exception {
        try{
            List<Serializable> lRequete = new ArrayList<>();
            lRequete.add(DES.encrypt(auth,kcs));
            lRequete.add(ticketGrantingService);
            out.writeObject(lRequete);
            List<Serializable> lReponse= (List<Serializable>) in.readObject();
            return lReponse;
        } catch (IllegalBlockSizeException | NoSuchPaddingException | IOException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | ClassNotFoundException e) {
            throw new Exception(e);
        }
    }

}
