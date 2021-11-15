package view;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author noesc
 */
public class Connexion {
    
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Connexion()
    {
        socket = null;
        objectInputStream = null;
        objectOutputStream = null;
    }
    
    public void connectToServer(String ip, int port) throws IOException {
        //Connection au serveur
            socket = new Socket(ip, port);
            System.out.println("Client connecté : " + socket.getInetAddress().toString());

        
        //Récupération des flux
            objectOutputStream = new ObjectOutputStream(getSocket().getOutputStream());
            objectInputStream = new ObjectInputStream(getSocket().getInputStream());

    }
    
    public void send_object(Object object) throws IOException {
        
        try 
        {
            //Envoi au serveur de l'objet
            objectOutputStream.writeObject(object);

            System.out.println("Object sent");
            
        } catch (IOException e)
        {
            System.err.println("Erreur d'envoi d'objet ! [" + e + "]");
            throw new IOException(e);
        }
    }

    public void reset_connection() throws IOException {
            socket.close();
    }

    public Socket getSocket() {
        return socket;
    }

    public InputStream getObjectInputStream() {
        return objectInputStream;
    }


    public OutputStream getObjectOutputStream() {
        return objectOutputStream;
    }


    public Object readObject(){

        Object obj = null;
        try
        {
            //Attente de l'objet
            //System.out.println("En attente d'un objet");
            obj = objectInputStream.readObject();

            System.out.println("Object recu!");
            return  obj;

        } catch (IOException e)
        {
            System.err.println("Erreur reception d'objet ! [" + e + "]");
            //return obj;

        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
