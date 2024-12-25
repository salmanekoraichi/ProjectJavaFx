package server.projectfinal.DAO;

import server.projectfinal.Models.Inscription;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Models.Modul;


import java.util.List;

/**
 * This code is written by Salmane Koraichi
 **/
public interface InscriptionDAO extends CRUD<Inscription, Integer> {
    List<Etudiant> findEtudiantsByModuleId(int moduleId);
    List<Inscription> findByEtudiantId(int etudiantId);
    List<Modul> findModulesByEtudiantId(int etudiantId);

}