package server.projectfinal.DAO;

import server.projectfinal.Models.Modul;
import server.projectfinal.Models.Professeur;
import server.projectfinal.Utils.DBConnection;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 * This code is written by Salmane Koraichi
 **/
public class ProfesseurDAOImpl implements ProfesseurDAO {
    private final Connection connection = DBConnection.getInstance().getConnection();

    @Override
    public Professeur findById(Integer id) {
        String query = "SELECT * FROM professeurs WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Professeur(rs.getInt("id"), rs.getString("specialite"), rs.getString("nom"), rs.getString("prenom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Professeur findByUsername(String Username) {
        String query = "SELECT * FROM professeurs WHERE Username = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, Username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Professeur(rs.getInt("id"), rs.getString("specialite"), rs.getString("nom"), rs.getString("prenom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Professeur> findAll() {
        String query = "SELECT * FROM professeurs";
        List<Professeur> professeurs = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                professeurs.add(new Professeur(rs.getInt("id"), rs.getString("specialite"), rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professeurs;
    }

    @Override
    public void save(Professeur professeur) {
        String query = "INSERT INTO professeurs (specialite, nom, prenom) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, professeur.getSpecialite());
            pst.setString(2, professeur.getNom());
            pst.setString(3, professeur.getPrenom());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Professeur professeur) {
        String query = "UPDATE professeurs SET specialite = ?, nom = ?, prenom = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, professeur.getSpecialite());
            pst.setString(2, professeur.getNom());
            pst.setString(3, professeur.getPrenom());
            pst.setInt(4, professeur.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM professeurs WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet load() {
        String query = "SELECT * FROM professeurs";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Modul> findModulesByProfesseurId(int professeurId) {
        String query = "SELECT * FROM modules WHERE professeurId = ?";
        List<Modul> modules = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, professeurId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                modules.add(new Modul(rs.getInt("id"), rs.getString("nomModule"), rs.getString("codeModule"), rs.getInt("professeurId")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

}
