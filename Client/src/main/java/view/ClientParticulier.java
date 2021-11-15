/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Crypto.DES.DES;
import DTO.Article;
import DTO.ClientMapKeys;
import DTO.Erreur;

import javax.crypto.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Utilisateur
 */
public class ClientParticulier extends javax.swing.JFrame {

    /**
     * Creates new form ClientParticulier
     */
    
    private Vector<String> columns;
    private ClientMapKeys clientMapKeys;
    private List<Article> lArticle;
    private List<Article> lArticleToSend;
    private Vector<String> columnsJTableCommande;
    public ClientParticulier(ClientMapKeys clientMapKeys) {

        this.clientMapKeys = clientMapKeys;
        lArticleToSend = new ArrayList<>();
        Vector<Vector<String>> vArticle = null;
        try {
            vArticle = receiveListArticle();
            if(vArticle==null)
            {
                System.exit(0);
            }
        } catch (IllegalBlockSizeException | ClassNotFoundException | NoSuchProviderException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | IOException | NoSuchPaddingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Erreur lors de la réception de la liste");
        }
        for(Article a : lArticle)
            System.out.println(a);

        initComponents();



        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        columns = new Vector<String>();
        columns.add("Id");
        columns.add("Nom");
        columns.add("Quantite");
        columns.add("Bio");
        columnsJTableCommande = new Vector<>();
        columnsJTableCommande.add("Nom");
        columnsJTableCommande.add("Quantite");
        jTableProduits.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel dtm = new DefaultTableModel();
        jTableProduits.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(jTableProduits.getSelectedRow());
                int selectedRow = jTableProduits.getSelectedRow();
                if(selectedRow>0) {
                    jTextFieldProd.setText(lArticle.get(selectedRow).getNom());
                    System.out.println("SELECTED ROW Item : " + lArticle.get(selectedRow));
                }
            }
        });
        setjTableProduits(vArticle);
        jTextFieldProd.setEditable(false);



        jButtonAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButtonAjouterActionPerformed(e);
            }
        });

        cleanCommande();



        this.pack();
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new JScrollPane();
        jTableProduits = new JTable();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldProd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButtonAjouter = new javax.swing.JButton();
        jScrollPane2 = new JScrollPane();
        jTableCommande = new JTable();
        jButtonEnvoyer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Liste des produits");

        jTableProduits.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableProduits);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Produit :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Quantite :");

        jButtonAjouter.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonAjouter.setText("Ajouter");

        jTableCommande.setModel(new DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane2.setViewportView(jTableCommande);

        jButtonEnvoyer.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonEnvoyer.setText("Envoyer");
        jButtonEnvoyer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnvoyerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldProd, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jButtonEnvoyer, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(217, 217, 217))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(jButtonAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jButtonEnvoyer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButtonAjouterActionPerformed(ActionEvent evt)
    {
        String quantiteString = jTextField1.getText();
        String nomArticle = jTextFieldProd.getText();
        if(quantiteString.isEmpty() ||nomArticle.isEmpty())
        {
            JOptionPane.showMessageDialog(this,"Veuillez saisir la quantite voulue et/ou saisir l'article");
            return;
        }
        ///Recherche article
        Article test = lArticle.stream().filter(c->c.getNom().equals(nomArticle)).findFirst().get();
        Article a = new Article(lArticle.stream().filter(c->c.getNom().equals(nomArticle)).findFirst().get());
        int quantite = Integer.parseInt(quantiteString);
        if(a.getQuantite() < quantite)
        {
            JOptionPane.showMessageDialog(this,"La quantite demandée n'est pas disponible");
            return;
        }
        a.setQuantite(quantite);

        updateCommande(a);
        updateRowList(test, quantite);
    }
    private void jButtonEnvoyerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnvoyerActionPerformed
        ///@TODO ajouter le code

        if(lArticleToSend.size() == 0)
        {
            JOptionPane.showMessageDialog(this,"Veuillez entrer un élémément dans la liste");
            return;
        }

        Connexion connexionServeur = clientMapKeys.getMapConnexion().get("serveur_produits");
        List<Serializable> toSend = null;
        try{
            toSend = encryptLArticleToSend();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"Une erreur est survenue lors du chiffrement des données");
            return;
        }

        try {
            connexionServeur.send_object(toSend);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Echec d'envoi exception : "+e.getMessage());
            return;
        }

        List<Serializable> lReponse = (List<Serializable>) connexionServeur.readObject();
        Erreur erreur = (Erreur) lReponse.get(0);
        if(erreur.getCode()== Erreur.ERREUR)
        {
            JOptionPane.showMessageDialog(this,"Une erreur s'est produite lors de la transaction");
            return;
        }
        try {
            setjTableProduits(receiveListArticle());
        } catch (IllegalBlockSizeException | ClassNotFoundException | NoSuchProviderException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | IOException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        CommandeView command = new CommandeView(lArticleToSend,clientMapKeys);
        command.setVisible(true);
        command.setLocationRelativeTo(null);

        cleanCommande();

    }//GEN-LAST:event_jButtonEnvoyerActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAjouter;
    private javax.swing.JButton jButtonEnvoyer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private JTable jTableCommande;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable jTableProduits;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldProd;
    // End of variables declaration//GEN-END:variables



    private List<Serializable> encryptLArticleToSend() throws IllegalBlockSizeException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        SecretKey kcs = clientMapKeys.getMapKeys().get("serveur_produits");
        List<Serializable> lSerializable = new ArrayList<>();
        for(Article a : lArticleToSend)
        {
            lSerializable.add(DES.encrypt(a,kcs));
        }
        return lSerializable;
    }


    private List<Article> decryptListArticle(List<Serializable> receivedList) throws IllegalBlockSizeException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, NoSuchProviderException, ClassNotFoundException {
        SecretKey kcs = clientMapKeys.getMapKeys().get("serveur_produits");
        List<Article> lArticleReceived = new ArrayList<>();
        for(Serializable s : receivedList)
        {
            lArticleReceived.add((Article)DES.decrypt((SealedObject) s,kcs));
        }
        return lArticleReceived;
    }



    private Vector<Vector<String>> receiveListArticle() throws IllegalBlockSizeException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, NoSuchProviderException, ClassNotFoundException {
        Connexion connexion = clientMapKeys.getMapConnexion().get("serveur_produits");
        List<Serializable> lReceived =(List<Serializable>) connexion.readObject();
        Erreur erreur = (Erreur) lReceived.get(0);
        System.out.println(erreur);
        if(erreur.getCode()==Erreur.ERREUR)
        {
            JOptionPane.showMessageDialog(this,"Erreur lors de la réception de la liste : déconnexion");
            return null;
        }
        lReceived.remove(0);
        lArticle = decryptListArticle(lReceived);
        return getVectorStringFromLArticle();

    }
    private Vector<Vector<String>> getVectorStringFromLArticle()
    {
        Vector<Vector<String>> vArticle = new Vector<>();
        for (Article a : lArticle)
        {
            Vector<String> vTmp = new Vector<>();
            vTmp.add(String.valueOf(a.getId()));
            vTmp.add(a.getNom());
            vTmp.add(String.valueOf(a.getQuantite()));
            vTmp.add(a.isBio()==true? "Oui":"Non");
            vArticle.add(vTmp);
        }
        return vArticle;
    }

    private void setjTableProduits(Vector<Vector<String>> vArticle)
    {
        DefaultTableModel dtm = ((DefaultTableModel) jTableProduits.getModel());
        dtm.setDataVector(vArticle,columns);
        jTableProduits.setModel(dtm);

    }

    private void updateCommande(Article a)
    {
        lArticleToSend.add(a);
        ((DefaultTableModel)jTableCommande.getModel()).addRow(new Object[]{a.getNom(), String.valueOf(a.getQuantite())});
    }

    private void cleanCommande()
    {
        DefaultTableModel dtm = new DefaultTableModel(null, columnsJTableCommande);
        jTableCommande.setModel(dtm);

        lArticleToSend.clear();
    }

    private void updateRowList(Article a, int quantite)
    {
        int index =lArticle.indexOf(a);
        int newQuantite = a.getQuantite()-quantite;
        lArticle.get(index).setQuantite(newQuantite);

        ((DefaultTableModel) jTableProduits.getModel()).setValueAt(
            a.getId(),index,0
        );
        ((DefaultTableModel) jTableProduits.getModel()).setValueAt(
                a.getNom(),index,1
        );
        ((DefaultTableModel) jTableProduits.getModel()).setValueAt(
                a.getQuantite(),index,2
        );
        ((DefaultTableModel) jTableProduits.getModel()).setValueAt(
                a.isBio()==true?"Oui":"Non",index,3
        );

    }



    private void disconnect() throws IOException {
        System.out.println("Disconnect");
        Map<String,Connexion> mapCo = clientMapKeys.getMapConnexion();
        for(Map.Entry<String,Connexion> e :mapCo.entrySet())
        {
            e.getValue().reset_connection();
        }
    }



}

