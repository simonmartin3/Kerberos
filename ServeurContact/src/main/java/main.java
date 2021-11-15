import ConfigurationFileReader.PropertyLoader;
import Crypto.Digest.Digest;
import MySqlAccess.MysqlAccess;
import ServerThreadPool.ServerThreadPool;

import java.io.IOException;
import java.security.Security;
import java.sql.SQLException;
import java.util.Properties;

public class main {

    public static void main(String[] args)
    {
        Properties prop = null;
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            // Load du fichier properties
        prop = PropertyLoader.load("ServeurContact\\prop.txt");

        if (!initializeJDBC(prop.getProperty("urlDb"), prop.getProperty("usernameDb"), prop.getProperty("passwordDb"))) {
            System.out.println("Erreur lors de l'initialisation JDBC");
            return;
        }
        Digest.setDigestAlgorithm("MD5");
        System.out.println("Connexion MYSql recu");
        ServerThreadPool stp = new ServerThreadPool(prop.getProperty("ip_serveur_contacts"),Integer.valueOf(prop.getProperty("port_serveur_contacts")),Integer.valueOf(prop.getProperty("maxThread")));
        stp.start();
    }

    private static boolean initializeJDBC(String url, String username, String password)
    {
        try {
            MysqlAccess.initialize(url, username, password);
            if (!MysqlAccess.testConnection()) {
                return false;
            }
        }
        catch(SQLException exception)
        {
            System.out.println("SQLException : " + exception);
            return false;
        }
        catch(ClassNotFoundException exception)
        {
            System.out.println("ClassNotFoundException : " + exception);
            return false;
        }
        return true;
    }
}
