package DTO;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    private String nom;
    private byte[] passwordHash;
    private long t;
    private String adr;
    private String tgsName;

    public ClientRequest(String nom, byte[] passwordHash, long t, String adr, String tgsName) {
        this.nom = nom;
        this.passwordHash = passwordHash;
        this.t = t;
        this.adr = adr;
        this.tgsName = tgsName;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getTgsName() {
        return tgsName;
    }

    public void setTgsName(String tgsName) {
        this.tgsName = tgsName;
    }





}
