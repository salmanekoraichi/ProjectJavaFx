package server.projectfinal.Services;

import server.projectfinal.DAO.ModuleDAO;
import server.projectfinal.Models.Modul;
import server.projectfinal.Utils.DBConnection;

import java.sql.*;
import java.util.List;

/**
 * This code is written by Salmane Koraichi
 **/
public class ModuleService {
    private final ModuleDAO moduleDAO;

    public ModuleService(ModuleDAO moduleDAO) {
        this.moduleDAO = moduleDAO;
    }

    public Modul getModuleById(int id) {
        return moduleDAO.findById(id);
    }

    public List<Modul> getAllModules() {
        return moduleDAO.findAll();
    }

    public void addModule(Modul module) {
        moduleDAO.save(module);
    }

    public void updateModule(Modul module) {
        moduleDAO.update(module);
    }

    public void deleteModule(int id) {
        moduleDAO.delete(id);
    }

    public Modul getMostEnrolledModule() {
        return moduleDAO.findMostEnrolledModule();
    }

    public void assignProfessorToModule(int moduleId, int professeurId) {
        Modul module = moduleDAO.findById(moduleId);
        if (module != null) {
            module.setProfesseurId(professeurId);
            moduleDAO.update(module);
        } else {
            throw new IllegalArgumentException("Module not found");
        }
    }

    public ResultSet loadAllModules() {
        return moduleDAO.load();
    }

    public ResultSet searchModules(String query) throws SQLException {
        // Suppose columns: id, nomModule, codeModule, professeurId, ...
        // Maybe also join with professeur table to search by prof name.
        String sql =
                "SELECT m.* "
                        + "FROM module m "
                        + "JOIN professeur p ON m.professeur_id = p.id "
                        + "WHERE m.nomModule LIKE ? "
                        + "   OR m.codeModule LIKE ? "
                        + "   OR p.nom LIKE ? "
                        + "   OR p.prenom LIKE ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + query + "%");
        ps.setString(2, "%" + query + "%");
        ps.setString(3, "%" + query + "%");
        ps.setString(4, "%" + query + "%");

        return ps.executeQuery();
    }
}

