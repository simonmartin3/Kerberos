
import ServerThreadPool.ServerThreadPool;
import ServerThreadPool.PropertyLoader;

import java.security.Security;
import java.util.Properties;

public class main {

    public static void main(String[] args)
    {
        Properties prop = null;

        prop = PropertyLoader.load("TGS\\prop.txt");


        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        ServerThreadPool stp =new ServerThreadPool(prop.getProperty("ip_serveur_tgs"),Integer.valueOf(prop.getProperty("port_serveur_tgs")),Integer.valueOf(prop.getProperty("maxThread")));
        stp.run();
    }

}
