package Crypto.HMAC;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

public class Hmac {

    private static String hmacAlgorithm = "HMAC-MD5";
    private static String codeProvider = "BC";

    private static byte[] hash(byte[] toHash, List<byte[]> salt, SecretKey secretKey, boolean begin) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        Mac hmac = Mac.getInstance(hmacAlgorithm, codeProvider);
        hmac.init(secretKey);
        if(begin)
            hmac.update(toHash);
        for(byte[] b : salt)
            hmac.update(b);
        if(!begin)
            hmac.update(toHash);

        return hmac.doFinal();
    }
    public static boolean compareHash(byte[] hash1, byte[] hash2)
    {
        return MessageDigest.isEqual(hash1, hash2);
    }



    public static String getHmacAlgorithm() {
        return hmacAlgorithm;
    }

    public static String getCodeProvider() {
        return codeProvider;
    }
}
