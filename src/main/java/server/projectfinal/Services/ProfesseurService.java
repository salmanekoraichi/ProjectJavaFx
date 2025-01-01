package server.projectfinal.Services;

import server.projectfinal.DAO.ProfesseurDAO;
import server.projectfinal.Models.Modul;
import server.projectfinal.Models.Professeur;
import server.projectfinal.Utils.DBConnection;

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
                + "WHERE nom LIKE ? OR prenom LIKE ?";
        PreparedStatement ps = connection.prepareStatement(sql);

// Now you have *2* placeholders
        ps.setString(1, "%" + query + "%");
        ps.setString(2, "%" + query + "%");

        return ps.executeQuery();
    }

}
