package ServerThreadPool;

import ConfigurationFileReader.PropertyLoader;
import Crypto.Digest.Digest;
import Crypto.Keystore.Keystore;
import JDBCAccess.BeanBdAccessClient;
import WorkingServer.WorkingServer;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.beans.Beans;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
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


    public void run() {
        ///Récupération Ktgs => Keystore
        Properties prop=null;
        prop = PropertyLoader.load("AS\\prop.txt");
        System.out.println("port = " + port + " ip address = " + ipAdresse + " max thread = " + maxThread + "  serveur= ServeurAs ");
        SecretKey Ktgs = Keystore.LoadFromKeyStore(prop.getProperty("as_Ktgs_path"), prop.getProperty("as_Ktgs_password"),prop.getProperty("as_Ktgs_alias"));
        BeanBdAccessClient beanBdAccessClient = null;
        System.out.printf("ktgs="+Ktgs);
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxThread);
        try {
            serverSocket = new ServerSocket(port, maxThread, InetAddress.getByName(ipAdresse));
            beanBdAccessClient = (BeanBdAccessClient) Beans.instantiate(null,"JDBCAccess.BeanBdAccessClient");
            Digest.setDigestAlgorithm("MD5");
        } catch (Exception e) {
            printExceptionWithEnd(e);
        }
        while (true) {
            try {
                System.out.println("On attend un client");
                Socket newSocket = serverSocket.accept();

                WorkingServer ws = new WorkingServer(newSocket,Ktgs,beanBdAccessClient);

                threadPoolExecutor.execute(ws);

            }
            catch(Exception e)
            {
                System.out.println("Exception : " + e.getMessage());
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