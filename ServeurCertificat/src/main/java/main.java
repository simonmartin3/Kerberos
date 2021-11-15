import ConfigurationFileReader.PropertyLoader;
import Crypto.Digest.Digest;
import ServerThreadPool.ServerThreadPool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Security;
import java.util.Map;
import java.util.Properties;

public class main {

    public static void main(String[] args)
    {
        Properties prop = null;

        prop = PropertyLoader.load("ServeurCertificat\\prop.txt");

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        Digest.setDigestAlgorithm("MD5");

        ServerThreadPool stp = new ServerThreadPool(prop.getProperty("ip_serveur_certificats"), Integer.valueOf(prop.getProperty("port_serveur_certificats")),Integer.valueOf(prop.getProperty("maxThread")));
        stp.start();
    }
}
