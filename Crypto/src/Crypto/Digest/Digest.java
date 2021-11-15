package Crypto.Digest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

public class Digest {
    private static String digestAlgorithm = "SHA-256";
    private static String codeProvider = "BC";
    public static byte[] hash(byte[] toDigest, List<byte[]> salts, boolean begin) throws NoSuchAlgorithmException, NoSuchProviderException {
        MessageDigest d = MessageDigest.getInstance(digestAlgorithm,codeProvider);
        if(begin)
            d.update(toDigest);
        for(byte[] b : salts)
        {
            d.update(b);
        }
        if(!begin)
            d.update(toDigest);
        return d.digest();
    }

    public static byte[] hash(byte[] toDigest, byte[] salts, boolean begin) throws NoSuchAlgorithmException, NoSuchProviderException {
        MessageDigest d = MessageDigest.getInstance(digestAlgorithm,codeProvider);
        if(begin)
            d.update(toDigest);
        d.update(salts);
        if(!begin)
            d.update(toDigest);
        return d.digest();
    }

    public static byte[] convertLongToByteArray(long l) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream daos = new DataOutputStream(baos);
        daos.writeLong(l);
        return baos.toByteArray();
    }
    public static boolean compareHash(byte[] hash1, byte[] hash2)
    {
        return MessageDigest.isEqual(hash1, hash2);
    }



    public static void setDigestAlgorithm(String digest)
    {
        digestAlgorithm = digest;
    }

    public static void setCodeProvider(String code)
    {
        codeProvider = code;
    }
}
