package Crypto.Lib;

import java.util.Base64;

public class ConverterStringByte {

    public static String convertArrayByteToString(byte[] byteArray)
    {
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public static byte[] convertStringToArrayByte (String s)
    {
        return Base64.getDecoder().decode(s);
    }
}
