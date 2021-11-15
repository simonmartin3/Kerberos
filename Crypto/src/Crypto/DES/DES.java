package Crypto.DES;

import javax.crypto.*;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class DES {
    private static String codeProvider = "BC";
    public static SecretKey getSecretKey(byte[] key) throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = new SecureRandom(key);
        KeyGenerator kg = KeyGenerator.getInstance("DES",codeProvider);
        kg.init(sr);
        return kg.generateKey();
    }
    public static SecretKey getSecretKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = new SecureRandom();
        KeyGenerator kg = KeyGenerator.getInstance("DES",codeProvider);
        kg.init(sr);
        return kg.generateKey();
    }
    public static byte[] encrypt(byte[] toEncrypt, SecretKey sk) throws Exception
    {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding",codeProvider);

        cipher.init(Cipher.ENCRYPT_MODE, sk);
        return cipher.doFinal(toEncrypt);
    }

    public static byte[] decrypt(byte[] toDecrypte, SecretKey sk) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding",codeProvider);
        cipher.init(Cipher.DECRYPT_MODE,sk);
        return cipher.doFinal(toDecrypte);
    }


    public static SealedObject encrypt(Serializable object, SecretKey sk) throws IllegalBlockSizeException, IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance("DES",codeProvider);
        cipher.init(Cipher.ENCRYPT_MODE, sk);
        return new SealedObject(object, cipher);
    }
    public static Object decrypt(SealedObject object, SecretKey sk) throws IllegalBlockSizeException, IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, BadPaddingException, ClassNotFoundException {
        Cipher cipher = Cipher.getInstance("DES",codeProvider);
        cipher.init(Cipher.DECRYPT_MODE, sk);
        return object.getObject(cipher);
    }
    public static void setCodeProvider(String code)
    {
        codeProvider = code;
    }
}
