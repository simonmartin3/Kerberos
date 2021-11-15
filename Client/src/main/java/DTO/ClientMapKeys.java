package DTO;

import view.Connexion;

import javax.crypto.SecretKey;
import java.util.HashMap;

public class ClientMapKeys {
    public static final int PARTICULIER=1, GROSSISTE = 2;


    private String username;
    private String password;
    private int categorie;
    private HashMap<String, SecretKey> mapKeys;
    private HashMap<String, Connexion> mapConnexion;

    public ClientMapKeys(String username, String password, int categorie) {
        this.username = username;
        this.password = password;
        this.categorie = categorie;
    }



    public ClientMapKeys(String username, String password, int categorie, HashMap<String, SecretKey> mapKeys, HashMap<String, Connexion> mapConnexion) {
        this.username = username;
        this.password = password;
        this.categorie = categorie;
        this.mapKeys = mapKeys;
        this.mapConnexion = mapConnexion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public HashMap<String, SecretKey> getMapKeys() {
        return mapKeys;
    }

    public void setMapKeys(HashMap<String, SecretKey> mapKeys) {
        this.mapKeys = mapKeys;
    }

    public HashMap<String, Connexion> getMapConnexion() {
        return mapConnexion;
    }

    public void setMapConnexion(HashMap<String, Connexion> mapConnexion) {
        this.mapConnexion = mapConnexion;
    }

    public void addKey(String keyName, SecretKey key)
    {
        this.mapKeys.put(keyName,key);
    }

    public void addConnexion(String connexionName, Connexion connexion)
    {
        this.mapConnexion.put(connexionName, connexion);
    }
}
