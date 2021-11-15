package Crypto.Lib;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SecretKeyConverter {

    public static String secretKeyToString(SecretKey key)
    {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    public static SecretKey stringToSecretKey(String key, String algorithm)
    {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey,0,decodedKey.length, algorithm);
    }
}
