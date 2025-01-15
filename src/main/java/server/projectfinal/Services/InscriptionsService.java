package server.projectfinal.Services;

import server.projectfinal.DAO.InscriptionDAO;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Models.Inscription;
import server.projectfinal.Utils.DBConnection;
import java.sql.ResultSet;

import java.sql.*;
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

    /*public ResultSet searchInscriptions(String query) throws SQLException {
        // Example: search by etudiant’s name or module’s name or date
        // That means you probably need a JOIN with etudiant or module table, e.g.:

        String sql =
                "SELECT i.* "
                        + "FROM inscription i "
                        + "JOIN etudiant e ON i.etudiantId = e.id "
                        + "JOIN module m ON i.moduleId = m.id "
                        + "WHERE e.nom LIKE ? "
                        + "   OR e.prenom LIKE ? "
                        + "   OR m.libelle LIKE ? "
                        + "   OR i.dateInscription LIKE ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + query + "%");
        ps.setString(2, "%" + query + "%");
        ps.setString(3, "%" + query + "%");
        ps.setString(4, "%" + query + "%");

        return ps.executeQuery();
    }*/


    public void updateInscription(Inscription inscription) {
        inscriptionDAO.update(inscription);
    }


    public ResultSet searchInscriptions(String query) throws SQLException {
        String sql =
                "SELECT i.id, e.nom AS etudiantNom, m.nomModule AS moduleNom, i.dateInscription " +
                        "FROM inscriptions i " +
                        "JOIN etudiants e ON i.etudiantId = e.id " +
                        "JOIN modules m ON i.moduleId = m.id " +
                        "WHERE e.nom LIKE ? " +
                        "   OR e.prenom LIKE ? " +
                        "   OR m.nomModule LIKE ? " +
                        "   OR i.dateInscription LIKE ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + query + "%");
        ps.setString(2, "%" + query + "%");
        ps.setString(3, "%" + query + "%");
        ps.setString(4, "%" + query + "%");

        return ps.executeQuery();
    }

}
