package server.projectfinal.Models;

/**
 * This code is written by Salmane Koraichi
 **/
public class Inscription {
    private int id;
    private int etudiantId;
    private int moduleId;
    private String dateInscription;


    public Inscription(int id, int etudiantId, int moduleId, String dateInscription) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.moduleId = moduleId;
        this.dateInscription = dateInscription;
    }

    public Inscription() {
    }

    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    @Override
    public String toString() {
        return "etudiantId=" + etudiantId +
                ", moduleId=" + moduleId;
    }
}
