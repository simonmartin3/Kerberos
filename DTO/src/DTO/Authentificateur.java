package DTO;

import java.io.Serializable;

public class Authentificateur implements Serializable {
    private String nom;
    private long timpestamp;
    private byte[] checksum;

    public Authentificateur(String nom, long timpestamp, byte[] checksum) {
        this.nom = nom;
        this.timpestamp = timpestamp;
        this.checksum = checksum;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getTimpestamp() {
        return timpestamp;
    }

    public void setTimpestamp(long timpestamp) {
        this.timpestamp = timpestamp;
    }

    public byte[] getChecksum() {
        return checksum;
    }

    public void setChecksum(byte[] checksum) {
        this.checksum = checksum;
    }
}
