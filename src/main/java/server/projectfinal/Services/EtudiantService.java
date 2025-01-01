package server.projectfinal.Services;

import server.projectfinal.DAO.EtudiantDAO;
import server.projectfinal.DAO.InscriptionDAO;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Models.Modul;
import server.projectfinal.Utils.DBConnection;

import java.sql.*;
import java.util.List;

/**
 * This code is written by Salmane Koraichi
 **/
public class EtudiantService {
    private final EtudiantDAO etudiantDAO;
    private final InscriptionDAO inscriptionDAO;


    public EtudiantService(EtudiantDAO etudiantDAO, InscriptionDAO inscriptionDAO) {
        this.etudiantDAO = etudiantDAO;
        this.inscriptionDAO = inscriptionDAO;
    }


    public Etudiant getEtudiantById(int id) {
        return etudiantDAO.findById(id);
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantDAO.findAll();
    }

    public void addEtudiant(Etudiant etudiant) {
        etudiantDAO.save(etudiant);
    }

    public void updateEtudiant(Etudiant etudiant) {
        etudiantDAO.update(etudiant);
    }

    public void deleteEtudiant(int id) {
        etudiantDAO.delete(id);
    }

    public List<Etudiant> searchEtudiantsByPromotion(String promotion) {
        return etudiantDAO.findByPromotion(promotion);
    }

    public List<Modul> getModulesForEtudiant(int etudiantId) {
        return inscriptionDAO.findModulesByEtudiantId(etudiantId);
    }

    public ResultSet load(){
        return etudiantDAO.load();
    }


    public ResultSet searchEtudiants(String query) throws SQLException {
        // If you want multi-criteria: search by nom, prenom, promotion, or matricule, etc.
        String sql = "SELECT * FROM etudiants "
                + "WHERE nom LIKE ? "
                + "   OR prenom LIKE ? "
                + "   OR promotion LIKE ? "
                + "   OR matricule LIKE ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + query + "%");
        ps.setString(2, "%" + query + "%");
        ps.setString(3, "%" + query + "%");
        ps.setString(4, "%" + query + "%");
        return ps.executeQuery();
    }



}

