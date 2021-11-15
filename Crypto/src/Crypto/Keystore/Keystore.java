package Crypto.Keystore;

import javax.crypto.SecretKey;
import java.io.*;
import java.security.KeyStore;

public class Keystore {

    public static void StoreToKeyStore(SecretKey keyToStore, String pwd, String filepath, String alias) throws Exception {
        File file = new File(filepath);
        KeyStore javaKeyStore = KeyStore.getInstance("JCEKS");
        if (!file.exists()) {
            javaKeyStore.load(null, null);
        } else {
            InputStream inputStream = new FileInputStream(filepath);
            javaKeyStore.load(inputStream, pwd.toCharArray());
        }

        javaKeyStore.setKeyEntry(alias, keyToStore, pwd.toCharArray(), null);
        OutputStream writeStream = new FileOutputStream(filepath);
        javaKeyStore.store(writeStream, pwd.toCharArray());
    }

    public static SecretKey LoadFromKeyStore(String filepath, String pwd, String alias) {
        try {
            KeyStore keyStore = KeyStore.getInstance("JCEKS");
            InputStream inputStream = new FileInputStream(filepath);
            keyStore.load(inputStream, pwd.toCharArray());
            return (SecretKey) keyStore.getKey(alias, pwd.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}