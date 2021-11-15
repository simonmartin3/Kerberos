package DTO;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.security.Key;

public class KeyVersionServeur implements Serializable {

    private int version;
    private String nomDuServeur;
    private SecretKey secretKey;

    public KeyVersionServeur()
    {

    }
    public KeyVersionServeur(SecretKey secretKey, int version, String nomDuServeur) {
        this.secretKey = secretKey;
        this.version = version;
        this.nomDuServeur = nomDuServeur;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey kctgs) {
        secretKey = kctgs;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getNomDuServeur() {
        return nomDuServeur;
    }

    public void setNomDuServeur(String nomDuServeur) {
        this.nomDuServeur = nomDuServeur;
    }

    @Override
    public String toString() {
        return "KeyVersionServeur{" +
                "secretKey=" + secretKey +
                ", version=" + version +
                ", nomDuServeur='" + nomDuServeur + '\'' +
                '}';
    }
}
