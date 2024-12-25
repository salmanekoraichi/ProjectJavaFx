package server.projectfinal.Services;

import server.projectfinal.DAO.ProfesseurDAO;
import server.projectfinal.Models.Modul;
import server.projectfinal.Models.Professeur;

import java.util.List;

/**
 * This code is written by Salmane Koraichi
 **/
public class ProfesseurService {
    private final ProfesseurDAO professeurDAO;

    public ProfesseurService(ProfesseurDAO professeurDAO) {
        this.professeurDAO = professeurDAO;
    }

    public Professeur getProfesseurById(int id) {
        return professeurDAO.findById(id);
    }

    public List<Professeur> getAllProfesseurs() {
        return professeurDAO.findAll();
    }

    public void addProfesseur(Professeur professeur) {
        professeurDAO.save(professeur);
    }

    public void updateProfesseur(Professeur professeur) {
        professeurDAO.update(professeur);
    }

    public void deleteProfesseur(int id) {
        professeurDAO.delete(id);
    }

    public List<Modul> getModulesByProfesseur(int professeurId) {
        return professeurDAO.findModulesByProfesseurId(professeurId);
    }
}
