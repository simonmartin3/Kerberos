package WorkingServer;

import Crypto.AES.AES;
import Crypto.DES.DES;
import Crypto.Digest.Digest;
import Crypto.Keystore.Keystore;
import DTO.*;
import ServerThreadPool.PropertyLoader;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WorkingServer implements Runnable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private SecretKey ktgs;

    public WorkingServer(Socket s, SecretKey ktgs) throws IOException
    {
        this.socket =s;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        this.ktgs = ktgs;
    }

    @Override
    public void run() {

        Properties prop = null;
        prop = PropertyLoader.load("TGS\\prop.txt");

        List<Serializable> lResponse = new ArrayList<>();
        try{
            ///Réception List<Serializable> contenant :
            ///0 : Acs => {nc, t, ck} K_ctgs => Authentificateur
            ///1: T_ctgs = tgs, {nc,a, tv, K_ctgs}Ktgs (TicketGrantingService)
            ///2: nom ns
            List<Serializable> lReception = (List<Serializable>) in.readObject();

            TicketGrantingService Tctgs = (TicketGrantingService)lReception.get(1);
            TicketGrantingServiceCrypted TctgsCrypted =(TicketGrantingServiceCrypted) AES.decrypt(Tctgs.getTicketGrantingServiceCrypted(),ktgs);

            long timestamp = TctgsCrypted.getTimestamp();
            if(!timestampIsValid(timestamp, 8))
            {
                throw new TimestampException("Invalid timestamp");
            }
            SecretKey Kctgs = TctgsCrypted.getSecretKey();
            System.out.println("Kctgs = " + ktgs);
            // Authentificateur {nom, date-heure, checksum (nom+ date-heure)MD5)}K_ctgs / TicketClientTgs /
            SealedObject authSealed = (SealedObject) lReception.get(0);
            ///Déchiffrement l'authentificateur (grâce à K_CTGS)
            Authentificateur auth = (Authentificateur) DES.decrypt(authSealed,Kctgs);

            ///On recrée le checksum
            byte[] ck1= auth.getChecksum();
            byte[] ck2 = Digest.hash(auth.getNom().getBytes(StandardCharsets.UTF_8), Digest.convertLongToByteArray(auth.getTimpestamp()),true);
            ///Verification checksum
            if(!Digest.compareHash(ck1,ck2))
            {
                throw new ChecksumException("Checksum invalid");
            }
            ///Si OK
                ///Récupération Ks du serveur visé (s'il existe) => Keystore
            String ns = (String)lReception.get(2);
            System.out.println("NS : " + ns);
            SecretKey Ks = Keystore.LoadFromKeyStore(prop.getProperty("tgs_ks_path"), prop.getProperty("tgs_ks_password"),ns);
            System.out.println("Ks = " + Ks);
            System.out.println("Ks = " + Base64.getEncoder().encodeToString(Ks.getEncoded()));

            ///Génération Kcs

            SecretKey Kcs = DES.getSecretKey();
            System.out.println("Kcs = "+ Kcs);
            /// KcsVersionServeur {Kcs, version, nom du serveur}Kc_tgs
                   ///TicketTCS (serveur,{Kcs, nom du client, limite validité ticket, ...} Ks)
            KeyVersionServeur kvs = new KeyVersionServeur(Kcs, 1, ns);
            TicketGrantingService tcs = new TicketGrantingService(ns,AES.encrypt(
                    new TicketGrantingServiceCrypted(Kcs,auth.getNom(),System.currentTimeMillis()),Ks));
            lResponse.add(new Erreur("Success", Erreur.SUCCESS));
            lResponse.add(DES.encrypt(kvs, Kctgs));
            lResponse.add(tcs);

        }
        catch(TimestampException te)
        {
            lResponse.add(new Erreur("Timestamp exception",Erreur.ERREUR));
        }
        catch(ChecksumException cke)
        {
            lResponse.add(new Erreur("Checksum exception",Erreur.ERREUR));
        }
        catch(Exception e)
        {
            log(e.getMessage());
            lResponse.add(new Erreur("Une exception s'est produite",Erreur.ERREUR));
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
        System.out.println(")"+message);
    }


    public static final long HOUR = 3600*1000;

    private boolean timestampIsValid(long l1, int interval)
    {
        return ((new Date().getTime()-l1)/HOUR) < interval;
    }
}
