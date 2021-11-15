package DTO;

import java.util.Map;

public class ClientAuthentifier {

    private String nom;
    private int categorie;
    private Map<String, KeyVersionServeur> mapKvs;
    private Map<String, TicketGrantingService> tgs;

    public ClientAuthentifier() {
    }

    public ClientAuthentifier(String nom, int categorie, Map<String, KeyVersionServeur> mapKvs, Map<String, TicketGrantingService> tgs) {
        this.nom = nom;
        this.categorie = categorie;
        this.mapKvs = mapKvs;
        this.tgs = tgs;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public Map<String, KeyVersionServeur> getMapKvs() {
        return mapKvs;
    }

    public void setMapKvs(Map<String, KeyVersionServeur> mapKvs) {
        this.mapKvs = mapKvs;
    }

    public Map<String, TicketGrantingService> getTgs() {
        return tgs;
    }

    public void setTgs(Map<String, TicketGrantingService> tgs) {
        this.tgs = tgs;
    }

    @Override
    public String toString() {
        return "ClientAuthentifier{" +
                "nom='" + nom + '\'' +
                ", categorie=" + categorie +
                ", mapKvs=" + mapKvs +
                ", tgs=" + tgs +
                '}';
    }
}
