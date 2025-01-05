package server.projectfinal.Models;

/**
 * This code is written by Salmane Koraichi
 **/
public class Professeur {
    private int id;
    private String nom;
    private String prenom;
    private String specialite;
    private String Username;

    public Professeur() {
    }

    public Professeur(int id, String specialite, String nom, String prenom) {
        this.id = id;
        this.specialite = specialite;
        this.nom = nom;
        this.prenom = prenom;
        this.Username = nom+prenom;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        Username = username;
    }


    @Override
    public String toString() {
        return "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom ;
    }

}
