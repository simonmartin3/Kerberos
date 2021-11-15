package ServerThreadPool;
import ConfigurationFileReader.PropertyLoader;
import Crypto.Keystore.Keystore;
import WorkingServer.WorkingServer;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.beans.Beans;
import java.net.*;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerThreadPool{

    private String ipAdresse;
    private int port;
    private int maxThread;
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPoolExecutor = null;

    public ServerThreadPool(String ip, int p, int mt)
    {
        ipAdresse = ip;
        port = p;
        maxThread = mt;
    }

    public void start() {

        Properties prop = null;
        prop = PropertyLoader.load("ServeurCertificat\\prop.txt");

        SecretKey ks = Keystore.LoadFromKeyStore(prop.getProperty("certificat_ks_path"), prop.getProperty("certificat_ks_password"),prop.getProperty("certificat_ks_alias"));
        System.out.println("Ks = "+ks);

        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxThread);
        try {
            System.out.println("port = " + port + " ip address = " + ipAdresse + " max thread = " + maxThread + "  serveur= ServeurCertificat ");
            serverSocket = new ServerSocket(port, maxThread, InetAddress.getByName(ipAdresse));
        } catch (Exception e) {
            printExceptionWithEnd(e);
        }
        while (true) {
            try {
                System.out.println("On attend un client");
                Socket newSocket = serverSocket.accept();

                WorkingServer ws = new WorkingServer(newSocket, ks);

                threadPoolExecutor.execute(ws);

            }
            catch(Exception e)
            {
                System.out.printf("voir eurreur"+e.getMessage());
            }
        }
    }



    synchronized private void printExceptionWithEnd(Exception ex)
    {
        endProperly();
    }
    private void endProperly()
    {
        System.exit(-1);
    }
    public String getIpAdresse() {
        return ipAdresse;
    }

    public void setIpAdresse(String ipAdresse) {
        this.ipAdresse = ipAdresse;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }


    public void shutdown()
    {
        System.out.println("SHUTDOWN");
        if(threadPoolExecutor != null) {
            threadPoolExecutor.shutdownNow();
            System.out.println("ThreadPoolExecutor shutdown");
        }
        System.exit(-1);
    }

}
