package server.projectfinal.Services;

import server.projectfinal.DAO.ProfesseurDAO;
import server.projectfinal.DAO.UtilisateurDAOImpl;
import server.projectfinal.Models.Modul;
import server.projectfinal.Models.Professeur;
import server.projectfinal.Models.Utilisateur;
import server.projectfinal.Utils.DBConnection;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
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
        UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername(professeur.getUsername());
        //should be random
        utilisateur.setPassword(professeur.getUsername());
        utilisateur.setRole("professor");
        utilisateurDAO.save(utilisateur);
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



    public ResultSet load(){
        return professeurDAO.load();
    }

    public ResultSet searchProfesseurs(String query) throws SQLException {
        // Example pseudo-code:
        // This could look for partial matches in name, or filter by module taught.
        // Suppose your "professeur" table has columns: id, nom, prenom, ...
        // And you want to find any row where nom LIKE '%query%' OR prenom LIKE '%query%', etc.
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM professeurs "
                + "WHERE nom LIKE ? OR prenom LIKE ? OR specialite LIKE ? ";

        PreparedStatement ps = connection.prepareStatement(sql);

// Now you have *2* placeholders
        ps.setString(1, "%" + query + "%");
        ps.setString(2, "%" + query + "%");
        ps.setString(3, "%" + query + "%");


        return ps.executeQuery();
    }


    public int findidbyusername(String username) {
        return professeurDAO.findidbyusername(username);
    }

    public ResultSet getMdsbyid(int id){
        return professeurDAO.getMdsbyid(id);
    }

    public ResultSet getModsbyid(int professeurId) {
        String query = "SELECT * FROM modules WHERE professeurId = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, professeurId);
            ResultSet rs = stmt.executeQuery();

            // Create a CachedRowSet and populate it with the ResultSet data
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet crs = factory.createCachedRowSet();
            crs.populate(rs);

            return crs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
//here

    public ResultSet GetEtudiantsById(int id){
        String query = "SELECT * FROM etudiants WHERE etudiants.id IN ( SELECT inscriptions.etudiantId FROM inscriptions WHERE inscriptions.moduleId IN ( SELECT modules.id FROM modules WHERE professeurId = ?))";
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // Create a CachedRowSet and populate it with the ResultSet data
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet crs = factory.createCachedRowSet();
            crs.populate(rs);

            return crs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } }
}
