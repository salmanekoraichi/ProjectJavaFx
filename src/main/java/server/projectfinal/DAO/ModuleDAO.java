package server.projectfinal.DAO;

import server.projectfinal.Models.Modul;

/**
 * This code is written by Salmane Koraichi
 **/
public interface ModuleDAO extends CRUD<Modul, Integer> {
    Modul findMostEnrolledModule();
}
