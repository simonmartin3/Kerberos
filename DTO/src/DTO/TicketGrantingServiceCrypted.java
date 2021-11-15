package DTO;

import javax.crypto.SecretKey;
import java.io.Serializable;

public class TicketGrantingServiceCrypted implements Serializable {
    private SecretKey secretKey;
    private String nomDuClient;
    private long timestamp;

    public TicketGrantingServiceCrypted(SecretKey kcTgs, String nomDuClient, long timestamp) {
        secretKey = kcTgs;
        this.nomDuClient = nomDuClient;
        this.timestamp = timestamp;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String getNomDuClient() {
        return nomDuClient;
    }

    public void setNomDuClient(String nomDuClient) {
        this.nomDuClient = nomDuClient;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TicketGrantingServiceCrypted{" +
                "secretKey=" + secretKey +
                ", nomDuClient='" + nomDuClient + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
