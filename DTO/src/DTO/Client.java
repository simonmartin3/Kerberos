package DTO;

public class Client {
    private int id;
    private String nom;
    private int categorie;
    private String pwd;

    public Client(int id, String nom, int categorie, String pwd) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
