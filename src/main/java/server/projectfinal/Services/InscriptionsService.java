package server.projectfinal.Services;

import server.projectfinal.DAO.InscriptionDAO;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Models.Inscription;
import java.sql.ResultSet;

import java.util.List;

/**
 * This code is written by Salmane Koraichi
 **/
public class InscriptionsService {
    private final InscriptionDAO inscriptionDAO;

    public InscriptionsService(InscriptionDAO inscriptionDAO) {
        this.inscriptionDAO = inscriptionDAO;
    }

    public Inscription getInscriptionById(int id) {
        return inscriptionDAO.findById(id);
    }

    public void addInscription(Inscription inscription) {
        List<Etudiant> etudiants = inscriptionDAO.findEtudiantsByModuleId(inscription.getModuleId());
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getId() == inscription.getEtudiantId()) {
                throw new IllegalArgumentException("Etudiant already enrolled in this module");
            }
        }
        inscriptionDAO.save(inscription);
    }

    public void deleteInscription(int id) {
        inscriptionDAO.delete(id);
    }

    public List<Etudiant> getEtudiantsForModule(int moduleId) {
        return inscriptionDAO.findEtudiantsByModuleId(moduleId);
    }

    public  List<Inscription> getAllInscriptions(){
        return inscriptionDAO.findAll();
    }

    public ResultSet load(){
        return inscriptionDAO.load();
    }
}
