package DTO;

import java.io.Serializable;
import java.util.Objects;

public class Article implements Serializable {

    private int id;
    private String nom;
    private int quantite;
    private boolean bio;

    public Article(int id, String nom, int quantite, boolean bio)
    {
        setId(id);
        setNom(nom);
        setQuantite(quantite);
        setBio(bio);
    }

    public Article(Article article) {
        setId(article.getId());
        setNom(article.getNom());
        setQuantite(article.getQuantite());
        setBio(article.isBio());
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public boolean isBio() {
        return bio;
    }

    public void setBio(boolean bio) {
        this.bio = bio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id && quantite == article.quantite && bio == article.bio && nom.equals(article.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, quantite, bio);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", quantite=" + quantite +
                ", bio=" + bio +
                '}';
    }
}
