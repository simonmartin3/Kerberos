/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Crypto.AES.AES;
import Crypto.DES.DES;
import Crypto.Digest.Digest;
import Crypto.Keystore.Keystore;
import DTO.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import utility.JsonDeserializer.CustomClientAuthentifierDeserializer;
import utility.JsonSerializer.CustomClientAuthentifierSerializer;
import utility.JsonSerializer.PropertyLoader;

import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;

/**
 *
 * @author Utilisateur
 */
public class ClientFen extends javax.swing.JFrame {

    Properties prop = null;
    /**
     * Creates new form ClientFen
     */
    public ClientFen() {
        initComponents();
            // Load du fichier properties
        prop = PropertyLoader.load("client\\prop.txt");

        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldIdentifiant = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField = new javax.swing.JPasswordField();
        jLabelReponse = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("Connexion");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Connexion au serveur");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Identifiant : ");

        jTextFieldIdentifiant.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Mot de passe :");

        jPasswordField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabelReponse, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldIdentifiant, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPasswordField))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldIdentifiant, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelReponse, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String login = jTextFieldIdentifiant.getText();
        String password = new String (jPasswordField.getPassword());
        System.out.println("Id = " + login + "--- password :"+ password);
        if (login.isEmpty() || password.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Veuillez saisir un login et un mot de passe ");
            return;
        }
        ///Connexion serveur
        /// Client cli = methodX(...)
        ClientMapKeys clientMapKeys = connect(login, password);
        if(clientMapKeys != null)
        {
            if(clientMapKeys.getCategorie()==1)
            {
                ///if cli.categorie == 1
                ///particulier
                this.setVisible(false);
                ClientParticulier clp = new ClientParticulier(clientMapKeys);
                clp.setVisible(true);
                clp.setTitle("Client particulier");
                clp.setLocationRelativeTo(null);
            }
            else{
                this.setVisible(false);
                ClientGrossiste clg = new ClientGrossiste(clientMapKeys);
                clg.setVisible(true);
                clg.setTitle("Client grossiste");
                clg.setLocationRelativeTo(null);
            }


        }



    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelReponse;
    private javax.swing.JTextField jTextFieldIdentifiant;
    private javax.swing.JPasswordField jPasswordField;
    // End of variables declaration//GEN-END:variables


    private ClientMapKeys connect(String username, String password) {
        ClientMapKeys cli = null;

            ///Check fichier (recherche de ticket)
        String filePath = prop.getProperty("clientStoreTicket")+"/"+username+".json";
        if(!new File(filePath).exists())
        {
            System.out.println("ON N'A PAS LE TICKET");
            cli=connectStartingFromAs(username, password, filePath);
        }
        else{
            System.out.println("ON A DEJA LE TICKET");
            cli= connectStartingFromServer(username, password, filePath);

            if(cli==null)
            {
                if(!getKeyTicketFromTGS(username,password,filePath)) {
                    if (!deleteFileClient(filePath)) {
                        System.out.println("Erreur lors de la suppression du fichier: " + filePath);
                        JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du fichier");
                        System.exit(0);
                    }
                }

                cli= connect(username,password);
            }
        }

return cli;
    }
    private boolean getKeyTicketFromTGS(String username, String password, String filepath)
    {
        ///R??cup??ration ClientAuthentifier

        ClientAuthentifier ca = null;
        try {
            ca = deserializeClient(filepath);


        // R??cup??ration des maps
        Map<String, KeyVersionServeur> mapKeyVersionServeur = ca.getMapKvs();
        Map<String, TicketGrantingService> mapTicketGrantingService = ca.getTgs();
            if(mapKeyVersionServeur.get("tgs") == null)
                return false;
        /// R??cup??ration KeyVersionServeur et TicketGrantingServiceTgs
            KeyVersionServeur kvsTgs = mapKeyVersionServeur.get("tgs");
            TicketGrantingService ticketGrantingService = mapTicketGrantingService.get("tgs");
            int categorie= ca.getCategorie();
        ///On boucle pour r??cup??rer tous les tickets et keybersion
            String[] serverNames = {"serveur_produits","serveur_contacts","serveur_certificats"};
            int nbServeurs = categorie==ClientMapKeys.PARTICULIER?1:3;
            List<Serializable> lReponse = new ArrayList<>();
            for(int i=0;i<nbServeurs;i++) {

                String serverName = serverNames[i];
                lReponse = connectToTGS(username, serverName, kvsTgs, ticketGrantingService);
                Erreur erreur = (Erreur) lReponse.get(0);
                if (erreur.getCode() == Erreur.ERREUR) {
                    throw new InvalidConnection("La connexion au serveur tgs a ??chou??");
                }
                ///Succ??s
                KeyVersionServeur keyVersionServeur = (KeyVersionServeur) DES.decrypt((SealedObject) lReponse.get(1), kvsTgs.getSecretKey());
                System.out.println(keyVersionServeur);

                TicketGrantingService tcs = (TicketGrantingService) lReponse.get(2);

                mapKeyVersionServeur.put(serverName, keyVersionServeur);
                mapTicketGrantingService.put(serverName, tcs);
            }
        ///Si tout va bien , on s??rialise
            ca.setTgs(mapTicketGrantingService);
            ca.setMapKvs(mapKeyVersionServeur);
            serializeClient(filepath,ca);
            /// On return true
            return true;
        }
        catch (IOException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | NoSuchProviderException | InvalidKeyException | InvalidConnection | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        ///Sinon return false
    }
    private ClientMapKeys connectStartingFromServer(String username, String password, String filepath)
    {
        ClientMapKeys cli = null;

        try {
        ///On r??cup??re ClientAuthentfier

            ClientAuthentifier ca = deserializeClient(filepath);

        // R??cup??ration des maps
        Map<String, KeyVersionServeur> mapKeyVersionServeur = ca.getMapKvs();
        Map<String, TicketGrantingService> mapTicketGrantingService = ca.getTgs();

        // Connection aux diff??rents serveurs
            HashMap<String, SecretKey> mapKey = new HashMap<>();
            HashMap<String, Connexion> mapConnexion = new HashMap<>();
            System.out.println("Length mapKey : "+ mapKeyVersionServeur.size());
        for(Map.Entry<String,KeyVersionServeur> entryKvs : mapKeyVersionServeur.entrySet())
        {
            if(entryKvs.getKey().equals("tgs"))
                continue;
            ///R??cup??ration de la cl??
            SecretKey kcs = entryKvs.getValue().getSecretKey();
            String servername = entryKvs.getKey();
            System.err.println("Servername : "+servername);
            ///R??cup??ration du ticket
            TicketGrantingService tgs = mapTicketGrantingService.get(servername);
            long timestamp = System.currentTimeMillis();

            Connexion con = connectToServer(username,servername,timestamp, entryKvs.getValue(), tgs);
            List<Serializable>lReponse = (List<Serializable>) con.readObject();
            Erreur erreur = (Erreur) lReponse.get(0);
            if (erreur.getCode() == Erreur.ERREUR) {
                throw new InvalidConnection("La connexion au serveur "+servername+" a ??chou??");
            }
            ///Succ??s
            ///Check le timestamp
            long timestampReceived = (long) DES.decrypt((SealedObject) lReponse.get(1), kcs);
            if (timestampReceived != timestamp) {
                throw new TimestampException("Le timestamp recu ne correspond pas");
            }
            mapKey.put(servername, kcs);
            mapConnexion.put(servername,con);

        }

        // ClientAuthentifier -> ClientMapKeys
        if(!mapKey.isEmpty() && !mapConnexion.isEmpty())
            cli = new ClientMapKeys(username, password, ca.getCategorie(), mapKey, mapConnexion);
        } catch (IOException | InvalidConnection | BadPaddingException | TimestampException | ClassNotFoundException | InvalidKeyException | NoSuchProviderException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Connexion failed : " + e.getMessage());
        }
        return cli;
    }

    private ClientMapKeys connectStartingFromAs(String username, String password, String filePath)
    {
        ClientMapKeys cli = null;
///Si pas de ticket => AS
        Map<String, KeyVersionServeur> mapKeyVersionServeur = new HashMap<String, KeyVersionServeur>();
        Map<String, TicketGrantingService> mapTicketGrantingService = new HashMap<String, TicketGrantingService>();
        HashMap<String, SecretKey> mapKey = new HashMap<>();
        HashMap<String, Connexion> mapConnexion = new HashMap<>();
        try {
            String[] serverNames = {"serveur_produits","serveur_contacts","serveur_certificats"};
            List<Serializable> lReponse=connectToAS(username, password);
            Erreur erreur = (Erreur) lReponse.get(0);
            System.out.printf("erreur="+Erreur.ERREUR);
            if(erreur.getCode()==Erreur.ERREUR)
            {
                throw new InvalidConnection("La connexion au serveur AS a ??chou??");
            }
            ///Succ??s
            SecretKey Kc = Keystore.LoadFromKeyStore(prop.getProperty("ClientsKeystore"),"y8EsHzbs2N8WQfkK",username);

            KeyVersionServeur kvs = (KeyVersionServeur) AES.decrypt((SealedObject)lReponse.get(1),Kc);
            System.out.println(kvs);
            TicketGrantingService tgs = (TicketGrantingService) lReponse.get(2);
            System.out.println(tgs);
            System.out.println("Categorie : " + lReponse.get(3));
            SecretKey kctgs = kvs.getSecretKey();

            int categorie = (int) lReponse.get(3);
             int nbServeurs = categorie==ClientMapKeys.PARTICULIER?1:3;

            for(int i=0;i<nbServeurs;i++) {

                String serverName = serverNames[i];
                lReponse = connectToTGS(username, serverName, kvs, tgs);
                erreur = (Erreur) lReponse.get(0);
                if (erreur.getCode() == Erreur.ERREUR) {
                    throw new InvalidConnection("La connexion au serveur tgs a ??chou??");
                }
                ///Succ??s
                KeyVersionServeur keyVersionServeur = (KeyVersionServeur) DES.decrypt((SealedObject) lReponse.get(1), kctgs);
                System.out.println(keyVersionServeur);

                TicketGrantingService tcs = (TicketGrantingService) lReponse.get(2);


                SecretKey kcs = keyVersionServeur.getSecretKey();
                long time = System.currentTimeMillis();
                Connexion connectToServer = connectToServer(username, serverName, time, keyVersionServeur, tcs);
                lReponse = (List<Serializable>) connectToServer.readObject();
                erreur = (Erreur) lReponse.get(0);
                if (erreur.getCode() == Erreur.ERREUR) {
                    throw new InvalidConnection("La connexion au serveur "+serverName+" a ??chou?? : " + erreur.getMessage());
                }
                ///Succ??s
                ///Check le timestamp
                long timestampReceived = (long) DES.decrypt((SealedObject) lReponse.get(1), kcs);
                if (timestampReceived != time) {
                    throw new TimestampException("Le timestamp recu ne correspond pas");
                }
                mapKey.put(serverName, kcs);
                mapConnexion.put(serverName,connectToServer);
                mapKeyVersionServeur.put(serverName, keyVersionServeur);
                mapTicketGrantingService.put(serverName, tcs);
            }
            mapKeyVersionServeur.put("tgs",kvs);
            mapTicketGrantingService.put("tgs",tgs);
            cli = new ClientMapKeys(username,password,categorie,mapKey, mapConnexion);
            serializeClient(filePath,new ClientAuthentifier(cli.getUsername(), cli.getCategorie(), mapKeyVersionServeur, mapTicketGrantingService));

        } catch (IOException | NoSuchAlgorithmException | NoSuchProviderException | InvalidConnection | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | ClassNotFoundException | TimestampException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"La connexion a echou?? : " + e.getMessage());
        }
        return cli;
    }

    private void serializeClient(String filepath,ClientAuthentifier clientAuthentifier) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ClientAuthentifier.class, new CustomClientAuthentifierSerializer());
        mapper.registerModule(module);
        System.out.println("Filepath = " + filepath);
        mapper.writeValue(new File(filepath),clientAuthentifier);
    }
    private ClientAuthentifier deserializeClient (String filepath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ClientAuthentifier.class ,new CustomClientAuthentifierDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(new File(filepath), ClientAuthentifier.class);
    }
    private boolean deleteFileClient(String filepath)
    {
        File f = new File(filepath);
        return f.delete();
    }
    private List<Serializable> connectToAS(String username, String password) throws IOException, NoSuchAlgorithmException, NoSuchProviderException {

        List<Serializable> lRecu= null;

        Connexion clientToAS = new Connexion();
        clientToAS.connectToServer(prop.getProperty("ip_serveur_as"), Integer.parseInt(prop.getProperty("port_serveur_as")));

        long time = System.currentTimeMillis();
        byte[] pwdHash = Digest.hash(password.getBytes(StandardCharsets.UTF_8),Digest.convertLongToByteArray(time),true);
        ClientRequest cr = new ClientRequest(username,pwdHash,time,"localhost","tgs");

        clientToAS.send_object(cr);
        lRecu = (List<Serializable>) clientToAS.readObject();

        clientToAS.reset_connection();
        return lRecu;
    }

    private List<Serializable> connectToTGS(String username,String serverName,KeyVersionServeur kvs , TicketGrantingService tgs) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {
        List<Serializable> lRecu= null;
        System.out.println("Servername asked to tgs : " + serverName);
        Connexion clientToTgs = new Connexion();
        clientToTgs.connectToServer(prop.getProperty("ip_serveur_tgs"), Integer.parseInt(prop.getProperty("port_serveur_tgs")));

        SecretKey kctgs = kvs.getSecretKey();
        long time = System.currentTimeMillis();
        byte[] checksum = Digest.hash(username.getBytes(StandardCharsets.UTF_8),Digest.convertLongToByteArray(time),true);
        Authentificateur auth = new Authentificateur(username,time,checksum);
        List<Serializable> lRequete = new ArrayList<>();
        lRequete.add(DES.encrypt(auth,kctgs));
        lRequete.add(tgs);
        lRequete.add(serverName);
        clientToTgs.send_object(lRequete);

        lRecu =(List<Serializable>) clientToTgs.readObject();
        clientToTgs.reset_connection();
        return lRecu;
    }

    private Connexion connectToServer(String username,String serverName,long time, KeyVersionServeur kvs, TicketGrantingService tcs) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {
        byte[] checksum = Digest.hash(username.getBytes(StandardCharsets.UTF_8),Digest.convertLongToByteArray(time),true);
        Authentificateur auth = new Authentificateur(username,time,checksum);
        Connexion clientToServer = new Connexion();
        clientToServer.connectToServer(prop.getProperty("ip_"+serverName), Integer.parseInt(prop.getProperty("port_"+serverName)));
        List<Serializable> lRequete = new ArrayList<>();
       ;lRequete.add(DES.encrypt(auth,kvs.getSecretKey()));
        lRequete.add(tcs);

        clientToServer.send_object(lRequete);

        return clientToServer;
    }
}
