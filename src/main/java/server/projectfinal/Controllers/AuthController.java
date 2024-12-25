package server.projectfinal.Controllers;

import server.projectfinal.Models.Utilisateur;
import server.projectfinal.Services.UtilisateurService;

import java.util.List;

/**
 * This code is written by Salmane Koraichi
 **/
public class AuthController {
    private final UtilisateurService utilisateurService;

    public AuthController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public Utilisateur login(String username, String password) {
        try {
            return utilisateurService.authenticate(username, password);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void register(String username, String password, String role) {
        Utilisateur utilisateur = new Utilisateur(0, username, password, role);
        try {
            utilisateurService.registerUser(utilisateur);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    //Admin Feature: This can later be linked to an admin dashboard, where an administrator can view all registered users.
    public void listAllUsers() {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUsers();
        utilisateurs.forEach(user -> System.out.println(user));
    }

}
