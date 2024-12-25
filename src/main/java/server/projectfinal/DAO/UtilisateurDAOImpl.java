package server.projectfinal.DAO;

import server.projectfinal.Models.Utilisateur;
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
public class UtilisateurDAOImpl implements UtilisateurDAO {
        private final Connection connection = DBConnection.getInstance().getConnection();

        @Override
        public Utilisateur findById(Integer id) {
            String query = "SELECT * FROM utilisateurs WHERE id = ?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return new Utilisateur(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public List<Utilisateur> findAll() {
            String query = "SELECT * FROM utilisateurs";
            List<Utilisateur> utilisateurs = new ArrayList<>();
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    utilisateurs.add(new Utilisateur(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return utilisateurs;
        }

        @Override
        public void save(Utilisateur utilisateur) {
            String query = "INSERT INTO utilisateurs (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, utilisateur.getUsername());
                pst.setString(2, utilisateur.getPassword());
                pst.setString(3, utilisateur.getRole());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update(Utilisateur utilisateur) {
            String query = "UPDATE utilisateurs SET username = ?, password = ?, role = ? WHERE id = ?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, utilisateur.getUsername());
                pst.setString(2, utilisateur.getPassword());
                pst.setString(3, utilisateur.getRole());
                pst.setInt(4, utilisateur.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void delete(Integer id) {
            String query = "DELETE FROM utilisateurs WHERE id = ?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setInt(1, id);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public ResultSet load() {
            String query = "SELECT * FROM utilisateurs";
            try {
                Statement statement = connection.createStatement();
                return statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Utilisateur findByUsername(String username) {
            String query = "SELECT * FROM utilisateurs WHERE username = ?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return new Utilisateur(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
}
