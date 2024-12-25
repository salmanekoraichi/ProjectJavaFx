package server.projectfinal.Services;

import server.projectfinal.DAO.ModuleDAO;
import server.projectfinal.Models.Modul;

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
}

