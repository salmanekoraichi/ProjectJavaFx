package server.projectfinal.DAO;

import server.projectfinal.Models.Etudiant;

import java.util.List;

/**
 * This code is written by Salmane Koraichi
 **/
public interface EtudiantDAO extends CRUD<Etudiant,Integer> {
    List<Etudiant> findByPromotion(String promotion);
}
