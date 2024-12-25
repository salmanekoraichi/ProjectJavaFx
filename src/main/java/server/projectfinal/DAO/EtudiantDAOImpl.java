package server.projectfinal.DAO;

import server.projectfinal.Models.Etudiant;
import server.projectfinal.Utils.DBConnection;

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
public class EtudiantDAOImpl implements EtudiantDAO {
        private final Connection connection = DBConnection.getInstance().getConnection();

        @Override
        public Etudiant findById(Integer id) {
            String query = "SELECT * FROM etudiants WHERE id = ?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return new Etudiant(rs.getInt("id"), rs.getString("matricule"), rs.getString("nom"), rs.getString("prenom"), rs.getString("dateNaissance"), rs.getString("email"), rs.getString("promotion"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public List<Etudiant> findAll() {
            String query = "SELECT * FROM etudiants";
            List<Etudiant> etudiants = new ArrayList<>();
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    etudiants.add(new Etudiant(rs.getInt("id"), rs.getString("matricule"), rs.getString("nom"), rs.getString("prenom"), rs.getString("dateNaissance"), rs.getString("email"), rs.getString("promotion")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return etudiants;
        }

        @Override
        public void save(Etudiant etudiant) {
            String query = "INSERT INTO etudiants (matricule, nom, prenom, dateNaissance, email, promotion) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, etudiant.getMatricule());
                pst.setString(2, etudiant.getNom());
                pst.setString(3, etudiant.getPrenom());
                pst.setString(4, etudiant.getDateNaissance());
                pst.setString(5, etudiant.getEmail());
                pst.setString(6, etudiant.getPromotion());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update(Etudiant etudiant) {
            String query = "UPDATE etudiants SET matricule = ?, nom = ?, prenom = ?, dateNaissance = ?, email = ?, promotion = ? WHERE id = ?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, etudiant.getMatricule());
                pst.setString(2, etudiant.getNom());
                pst.setString(3, etudiant.getPrenom());
                pst.setString(4, etudiant.getDateNaissance());
                pst.setString(5, etudiant.getEmail());
                pst.setString(6, etudiant.getPromotion());
                pst.setInt(7, etudiant.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void delete(Integer id) {
            String query = "DELETE FROM etudiants WHERE id = ?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setInt(1, id);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public ResultSet load() {
            String query = "SELECT * FROM etudiants";
            try {
                Statement statement = connection.createStatement();
                return statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public List<Etudiant> findByPromotion(String promotion) {
            String query = "SELECT * FROM etudiants WHERE promotion = ?";
            List<Etudiant> etudiants = new ArrayList<>();
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, promotion);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    etudiants.add(new Etudiant(rs.getInt("id"), rs.getString("matricule"), rs.getString("nom"), rs.getString("prenom"), rs.getString("dateNaissance"), rs.getString("email"), rs.getString("promotion")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return etudiants;
        }

}
