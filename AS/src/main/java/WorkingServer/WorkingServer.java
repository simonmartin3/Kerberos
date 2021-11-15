package WorkingServer;

import ConfigurationFileReader.PropertyLoader;
import Crypto.AES.AES;
import Crypto.DES.DES;
import Crypto.Digest.Digest;
import Crypto.Keystore.Keystore;
import DTO.*;
import JDBCAccess.BeanBdAccessClient;
import MySqlAccess.MysqlAccess;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class WorkingServer implements Runnable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;
    private String password;
    private SecretKey Ktgs;
    private BeanBdAccessClient beanBdAccessClient;
    private Client client;
    public WorkingServer(Socket s, SecretKey ktgs, BeanBdAccessClient beanBdAccessClient) throws IOException
    {
        this.socket =s;
        this.Ktgs = ktgs;
        this.beanBdAccessClient = beanBdAccessClient;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        List<Serializable> lResponse = new ArrayList<>();
        try{
            Properties prop=null;
            prop = PropertyLoader.load("AS\\prop.txt");
            ///Réception Client (nom de compte, mot de pass(hash), date, adr, nom du serveur TGS => "tgs)
            ClientRequest clientRequest = (ClientRequest) in.readObject();
            /// Récupération du Client dans la db (Client (ndc, password (clair), categorie(1=> particulier, 2=>grossiste)
            client = beanBdAccessClient.getClient(MysqlAccess.getConnection(), clientRequest.getNom());
            if(client==null)
            {
                lResponse.add(new Erreur("Invalid credentials", Erreur.ERREUR));
            }
            else{
                /// Hash du mot de passe

                byte[] hashPassword= Digest.hash(client.getPwd().getBytes(StandardCharsets.UTF_8), Digest.convertLongToByteArray(clientRequest.getT()),true);
                ///Comparaison des hash
                if(Digest.compareHash(clientRequest.getPasswordHash(),hashPassword))
                {
                    ///Si OK

                    ///Récupération Kc depuis keystore (alias = username)
                    SecretKey Kc=Keystore.LoadFromKeyStore(prop.getProperty("as_Ktgs_path"),prop.getProperty("as_Ktgs_password"),client.getNom());


                    ///Génération K_ctgs(DES)
                    SecretKey Kctgs = DES.getSecretKey();

                    /// KeyVersionServeur(K_ctgs,version,nom du serveur TGS) / TcTGS(K_ctgs, nom du clientRequest, limite de validité) + catégorie clientRequest
                    ///List<SealedObject> => 1 = {K_ctgs, version, nom du serveur TGS}Kc(DES) / 2=>TcTgs (AES)
                    KeyVersionServeur keyVersionServeur = new KeyVersionServeur(Kctgs,1,"tgs");
                    TicketGrantingService tctgs = new TicketGrantingService("tgs", AES.encrypt(
                            new TicketGrantingServiceCrypted(Kctgs,client.getNom(),System.currentTimeMillis()),Ktgs));

                    lResponse.add(new Erreur("Success", Erreur.SUCCESS));
                    lResponse.add(AES.encrypt(keyVersionServeur,Kc));
                    lResponse.add(tctgs);
                    ///region
                    lResponse.add(client.getCategorie());
                    ///endregion

                }
                else{
                    lResponse.add(new Erreur("Invalid credentials", Erreur.ERREUR));

                }

            }

        }
        catch(Exception e)
        {
            log(e.getMessage());
            lResponse.add(new Erreur("Une exception s'est declenche", Erreur.ERREUR));
        }

        try{
            out.writeObject(lResponse);
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
        if(client != null)
            System.out.print(client.getNom() + ")");
        System.out.println(message);


    }
}
