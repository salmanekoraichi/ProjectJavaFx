package server.projectfinal.Models;

/**
 * This code is written by Salmane Koraichi
 **/
public class Modul {
    private int id;
    private String nomModule;
    private String codeModule;
    private int professeurId;



    public Modul(int id, String nomModule, String codeModule, int professeurId) {
        this.id = id;
        this.nomModule = nomModule;
        this.codeModule = codeModule;
        this.professeurId = professeurId;
    }

    public Modul() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomModule() {
        return nomModule;
    }

    public void setNomModule(String nomModule) {
        this.nomModule = nomModule;
    }

    public String getCodeModule() {
        return codeModule;
    }

    public void setCodeModule(String codeModule) {
        this.codeModule = codeModule;
    }

    public int getProfesseurId() {
        return professeurId;
    }

    public void setProfesseurId(int professeurId) {
        this.professeurId = professeurId;
    }
}
