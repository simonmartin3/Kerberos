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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Utilisateur
 */
public class CommandeView extends javax.swing.JFrame {

    JButton button = new JButton();
    private final JPanel topPanel;
    private final JTable table;
    private final JScrollPane scrollPane;
    private final Vector<String> columns;
    private String[][] data;
    private final List<Article> lArticle;
    private final ClientMapKeys clientMapKeys;

    public CommandeView(List<Article> lA, ClientMapKeys clientMapKeys) {
        this.lArticle = new ArrayList<>(lA);
        this.clientMapKeys = clientMapKeys;
        columns = new Vector<String>();
        columns.add("Id");
        columns.add("Nom");
        columns.add("Quantite");
        columns.add("Bio");
        columns.add("Action");
        Vector<Vector<String>> data = getVectorStringFromLArticle();
        setTitle("JButton dans JTable");
        setSize(500, 300);
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        DefaultTableModel model = new DefaultTableModel(data, columns);
        table = new JTable();
        table.setModel(model);
        if (clientMapKeys.getCategorie() == ClientMapKeys.GROSSISTE) {
            System.out.println("Larticle : " + lArticle.size());
            System.out.println("LArticle 0 bio : " + lArticle.get(0).isBio());
           table.getColumn("Action").setCellRenderer(new ButtonRenderer(this.lArticle));
           table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(),this.lArticle));
        }


        scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                      System.out.println("Test button : " + button.isEnabled());
                        System.out.println("Ligne sélectionné : " + table.getSelectedRow());
                        ///Lancement du téléchargement
                        String filepath = null;
                        JFileChooser fc = new JFileChooser();
                        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                        {
                            filepath = fc.getSelectedFile().getPath();
                        }
                        if(filepath != null) {
                            Article articleToSend = lArticle.get(table.getSelectedRow());
                            Connexion connexion = clientMapKeys.getMapConnexion().get("serveur_certificats");
                            SecretKey kcs = clientMapKeys.getMapKeys().get("serveur_certificats");
                            filepath +="/cert_"+articleToSend.getNom()+".pdf";
                            try {
                                connexion.send_object(DES.encrypt(articleToSend, kcs ));
                            } catch (IllegalBlockSizeException | NoSuchProviderException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException e) {
                                e.printStackTrace();
                                System.out.println("Erreur lors de l'envoie de demande de certificats");
                                JOptionPane.showMessageDialog(null, "Erreur lors de l'envoie de demande de certificats");
                                return ;
                            }
                            List<Serializable> lReponse = (List<Serializable>)connexion.readObject();
                            Erreur erreur =(Erreur) lReponse.get(0);
                            if(erreur.getCode() == Erreur.ERREUR)
                            {
                                JOptionPane.showMessageDialog(null, "Erreur lors de la génération du certificats :" + erreur.getMessage());
                                System.out.println("Erreur lors de la génération du certificats : " + erreur.getMessage());
                            }
                            try {
                                Path path = Paths.get(filepath);
                                byte[] file = (byte[]) DES.decrypt((SealedObject) lReponse.get(1),clientMapKeys.getMapKeys().get("serveur_certificats"));
                                Files.write(path,file);
                            } catch (IllegalBlockSizeException | IOException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | NoSuchProviderException | BadPaddingException | ClassNotFoundException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null,"Erreur lors du déchiffrement du fichier : " + e.getMessage());
                            }


                            JOptionPane.showMessageDialog(null, "Le fichier a été sauvegardé dans votre repertoire");


                        }

                    }
                }
        );

    }

    void setLocationRelativeTo(float CENTER_ALIGNMENT) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Vector<Vector<String>> getVectorStringFromLArticle() {
        Vector<Vector<String>> vArticle = new Vector<>();
        for (Article a : lArticle) {
            Vector<String> vTmp = new Vector<>();
            vTmp.add(String.valueOf(a.getId()));
            vTmp.add(a.getNom());
            vTmp.add(String.valueOf(a.getQuantite()));
            vTmp.add(a.isBio() == true ? "Oui" : "Non");
            vArticle.add(vTmp);
        }
        return vArticle;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    class ButtonRenderer extends JButton implements TableCellRenderer {
        List<Article> lArticle;

        public ButtonRenderer(List<Article> lArt) {
            setOpaque(true);
            this.lArticle = new ArrayList<Article>(lArt);

        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Sauvegarder" : value.toString());
            System.out.println("RENDERED row : " + row + " column : " + column);
            System.out.println("Larticle : " + this.lArticle.size());
            System.out.println("LArticle 0 bio : " + this.lArticle.get(0).isBio());
            setEnabled(this.lArticle.get(row).isBio());

            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private List<Article> lArticle;
        public ButtonEditor(JCheckBox checkBox, List<Article> lArticle) {
            super(checkBox);
            this.lArticle = new ArrayList<>(lArticle);

        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "Sauvegarder" : value.toString();
            button.setText(label);
            button.setEnabled(lArticle.get(row).isBio());
            return button;

        }

        public Object getCellEditorValue() {
            return label;
        }
    }

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
