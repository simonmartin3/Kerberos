/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import Crypto.Digest.Digest;

import java.security.Security;

/**
 *
 * @author Utilisateur
 */
public class ClientMain {
    public static void main(String[] args){
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Digest.setDigestAlgorithm("MD5");

        ClientFen clientform = new ClientFen();
        clientform.setVisible(true);
        clientform.setLocationRelativeTo(null);
    }
}
