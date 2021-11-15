package Crypto.AES;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.Serializable;
import java.security.*;

public class AES {
    private static String codeProvider = "BC";
    public static SecretKey getKey(byte[] key) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator cleGen = KeyGenerator.getInstance("Rijndael", codeProvider);
        cleGen.init(128,new SecureRandom(key));
        return cleGen.generateKey();
    }
    public static SecretKey getKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator cleGen = KeyGenerator.getInstance("Rijndael", codeProvider);
        cleGen.init(128,new SecureRandom());
        return cleGen.generateKey();
    }

    public static byte[] encrypt(byte[] plainByte,SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher chiffrement = Cipher.getInstance("Rijndael/CBC/PKCS5Padding", codeProvider);
        byte[] vecteurInit = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(vecteurInit);
        chiffrement.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(vecteurInit));
        return chiffrement.doFinal(plainByte);
    }

    public static byte[] decrypt(byte[] cipher, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher dechiffrement = Cipher.getInstance("Rijndael/CBC/PKCS5Padding", codeProvider);
        byte[] vecteurInit = new byte[16];
        dechiffrement.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(vecteurInit));
        return dechiffrement.doFinal(cipher);
    }

    public static SealedObject encrypt(Serializable object, SecretKey sk) throws IllegalBlockSizeException, IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance("Rijndael",codeProvider);
        cipher.init(Cipher.ENCRYPT_MODE, sk);
        return new SealedObject(object, cipher);
    }
    public static Object decrypt(SealedObject object, SecretKey sk) throws IllegalBlockSizeException, IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, BadPaddingException, ClassNotFoundException {
        Cipher cipher = Cipher.getInstance("Rijndael",codeProvider);
        cipher.init(Cipher.DECRYPT_MODE, sk);
        return object.getObject(cipher);
    }

    public static void setCodeProvider(String code)
    {
        codeProvider = code;
    }
}
