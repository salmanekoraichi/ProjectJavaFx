package server.projectfinal.Models;

/**
 * This code is written by Salmane Koraichi
 **/
public class Etudiant {

    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String email;
    private String promotion;


    public Etudiant(int id, String matricule, String nom, String prenom, String dateNaissance, String email, String promotion) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.promotion = promotion;
    }

    public Etudiant() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", email='" + email + '\'' +
                ", promotion='" + promotion + '\'' +
                '}';
    }

}
