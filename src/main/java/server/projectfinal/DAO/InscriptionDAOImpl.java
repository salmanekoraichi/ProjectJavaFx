package server.projectfinal.DAO;

import server.projectfinal.Models.Inscription;
import server.projectfinal.Models.Modul;
import server.projectfinal.Utils.DBConnection;
import server.projectfinal.Models.Etudiant;


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
public class InscriptionDAOImpl implements InscriptionDAO {
    private final Connection connection = DBConnection.getInstance().getConnection();

    @Override
    public Inscription findById(Integer id) {
        String query = "SELECT * FROM inscriptions WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Inscription(rs.getInt("id"), rs.getInt("etudiantId"), rs.getInt("moduleId"), rs.getString("dateInscription"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Inscription> findAll() {
        String query = "SELECT * FROM inscriptions";
        List<Inscription> inscriptions = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                inscriptions.add(new Inscription(rs.getInt("id"), rs.getInt("etudiantId"), rs.getInt("moduleId"), rs.getString("dateInscription")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inscriptions;
    }

    @Override
    public void save(Inscription inscription) {
        String query = "INSERT INTO inscriptions (etudiantId, moduleId, dateInscription) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, inscription.getEtudiantId());
            pst.setInt(2, inscription.getModuleId());
            pst.setString(3, inscription.getDateInscription());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Inscription inscription) {
        String query = "UPDATE inscriptions SET etudiantId = ?, moduleId = ?, dateInscription = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, inscription.getEtudiantId());
            pst.setInt(2, inscription.getModuleId());
            pst.setString(3, inscription.getDateInscription());
            pst.setInt(4, inscription.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM inscriptions WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet load() {
        String query = "SELECT * FROM inscriptions";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Inscription> findByEtudiantId(int etudiantId) {
        String query = "SELECT * FROM inscriptions WHERE etudiantId = ?";
        List<Inscription> inscriptions = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, etudiantId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                inscriptions.add(new Inscription(rs.getInt("id"), rs.getInt("etudiantId"), rs.getInt("moduleId"), rs.getString("dateInscription")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inscriptions;
    }

    @Override
    public List<Etudiant> findEtudiantsByModuleId(int moduleId) {
        String query = "SELECT e.* FROM etudiants e JOIN inscriptions i ON e.id = i.etudiantId WHERE i.moduleId = ?";
        List<Etudiant> etudiants = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, moduleId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                etudiants.add(new Etudiant(rs.getInt("id"), rs.getString("matricule"), rs.getString("nom"), rs.getString("prenom"), rs.getString("dateNaissance"), rs.getString("email"), rs.getString("promotion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }

    @Override
    public List<Modul> findModulesByEtudiantId(int etudiantId) {
        String query = "SELECT m.* FROM moduls m JOIN inscriptions i ON m.id = i.moduleId WHERE i.etudiantId = ?";
        List<Modul> modules = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, etudiantId);
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
