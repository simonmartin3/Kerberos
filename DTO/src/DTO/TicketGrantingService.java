package DTO;

import javax.crypto.SealedObject;
import java.io.Serializable;

public class TicketGrantingService implements Serializable {

    private String nomServeur;
    private SealedObject ticketGrantingServiceCrypted;
    public TicketGrantingService()
    {}
    public TicketGrantingService(String nomServeur, SealedObject ticketGrantingServiceCrypted) {
        this.nomServeur = nomServeur;
        this.ticketGrantingServiceCrypted = ticketGrantingServiceCrypted;
    }

    public String getNomServeur() {
        return nomServeur;
    }

    public void setNomServeur(String nomServeur) {
        this.nomServeur = nomServeur;
    }

    public SealedObject getTicketGrantingServiceCrypted() {
        return ticketGrantingServiceCrypted;
    }

    public void setTicketGrantingServiceCrypted(SealedObject ticketGrantingServiceCrypted) {
        this.ticketGrantingServiceCrypted = ticketGrantingServiceCrypted;
    }

    @Override
    public String toString() {
        return "TicketGrantingService{" +
                "nomServeur='" + nomServeur + '\'' +
                ", ticketGrantingServiceCrypted=" + ticketGrantingServiceCrypted +
                '}';
    }
}
