package server.projectfinal.DAO;

import server.projectfinal.Models.Modul;
import server.projectfinal.Models.Professeur;

import java.sql.ResultSet;
import java.util.List;

/**
 * This code is written by Salmane Koraichi
 **/
public interface ProfesseurDAO extends CRUD<Professeur,Integer> {
    List<Modul> findModulesByProfesseurId(int professeurId);
    public Professeur findByUsername(String Username);
    public int findidbyusername(String username);
    public ResultSet getMdsbyid(int id);

}
