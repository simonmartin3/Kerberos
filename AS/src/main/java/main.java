
import ConfigurationFileReader.PropertyLoader;
import MySqlAccess.MysqlAccess;
import ServerThreadPool.ServerThreadPool;

import java.security.Security;
import java.sql.SQLException;
import java.util.Properties;

public class main {

    public static void main(String[] args)
    {
        Properties prop=null;

            prop = PropertyLoader.load("AS\\prop.txt");


        if(!initializeJDBC(prop.getProperty("urlAu"), prop.getProperty("usernameAu"),prop.getProperty("passwordAu")))
        {
            System.out.println("Erreur lors de l'initialisation JDBC");
            return;
        }
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        System.out.println("Connexion MYSql recu");
        ServerThreadPool stp = new ServerThreadPool(prop.getProperty("ip_serveur_as"), Integer.valueOf(prop.getProperty("port_serveur_as")),Integer.valueOf(prop.getProperty("maxThread")));
        stp.run();
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
