package server.projectfinal.Services;

import server.projectfinal.DAO.UtilisateurDAO;
import server.projectfinal.Models.Utilisateur;

import java.util.List;

/**
 * This code is written by Salmane Koraichi
 **/
public class UtilisateurService {
    private final UtilisateurDAO utilisateurDAO;

    public UtilisateurService(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    public Utilisateur getUtilisateurById(int id) {
        return utilisateurDAO.findById(id);
    }

    public Utilisateur getUtilisateurByUsername(String username) {
        return utilisateurDAO.findByUsername(username);
    }

    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.save(utilisateur);
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.update(utilisateur);
    }

    public void deleteUtilisateur(int id) {
        utilisateurDAO.delete(id);
    }

    public boolean hasRole(Utilisateur utilisateur, String role) {
        return utilisateur.getRole().equalsIgnoreCase(role);
    }

    public Utilisateur authenticate(String username, String password) {
        Utilisateur utilisateur = utilisateurDAO.findByUsername(username);
        if (utilisateur != null && utilisateur.getPassword().equals(password)) {
            return utilisateur;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }

    public void registerUser(Utilisateur utilisateur) {
        Utilisateur existingUser = utilisateurDAO.findByUsername(utilisateur.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        utilisateurDAO.save(utilisateur);
    }

    public List<Utilisateur> getAllUsers() {
        return utilisateurDAO.findAll();
    }

}

