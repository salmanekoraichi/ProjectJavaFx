package server.projectfinal.DAO;

import server.projectfinal.Models.Utilisateur;

/**
 * This code is written by Salmane Koraichi
 **/
public interface UtilisateurDAO extends CRUD<Utilisateur, Integer> {
    Utilisateur findByUsername(String username);
}