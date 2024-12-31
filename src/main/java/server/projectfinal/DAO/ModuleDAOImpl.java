package server.projectfinal.DAO;
import server.projectfinal.Models.Modul;
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
public class ModuleDAOImpl implements ModuleDAO {
    private final Connection connection = DBConnection.getInstance().getConnection();

    @Override
    public Modul findById(Integer id) {
        String query = "SELECT * FROM modules WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Modul(rs.getInt("id"), rs.getString("nomModule"), rs.getString("codeModule"), rs.getInt("professeurId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Modul> findAll() {
        String query = "SELECT * FROM modules";
        List<Modul> modules = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                modules.add(new Modul(rs.getInt("id"), rs.getString("nomModule"), rs.getString("codeModule"), rs.getInt("professeurId")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    @Override
    public void save(Modul modul) {
        String query = "INSERT INTO modules (nomModule, codeModule, professeurId) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, modul.getNomModule());
            pst.setString(2, modul.getCodeModule());
            pst.setInt(3, modul.getProfesseurId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Modul module) {
        String query = "UPDATE modules SET nomModule = ?, codeModule = ?, professeurId = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, module.getNomModule());
            pst.setString(2, module.getCodeModule());
            pst.setInt(3, module.getProfesseurId());
            pst.setInt(4, module.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM modules WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet load() {
        String query = "SELECT * FROM modules";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Modul findMostEnrolledModule() {
        String query = "SELECT m.*, COUNT(i.moduleId) as enrollment_count FROM modules m JOIN inscriptions i ON m.id = i.moduleId GROUP BY m.id ORDER BY enrollment_count DESC LIMIT 1";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return new Modul(rs.getInt("id"), rs.getString("nomModule"), rs.getString("codeModule"), rs.getInt("professeurId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
