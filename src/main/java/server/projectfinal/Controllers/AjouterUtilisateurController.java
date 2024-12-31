package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.projectfinal.Models.Utilisateur;
import server.projectfinal.Services.UtilisateurService;

public class AjouterUtilisateurController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ComboBox<String> roleCombo;

    private UtilisateurService utilisateurService;

    @FXML
    private void initialize() {
        // Example: fill roles
        roleCombo.getItems().addAll("admin", "professor", "student", "manager", "secretary");
    }

    public void setUtilisateurService(UtilisateurService service) {
        this.utilisateurService = service;
    }

    @FXML
    private void handleAddUser() {
        try {
            Utilisateur u = new Utilisateur();
            u.setUsername(usernameField.getText());
            u.setPassword(passwordField.getText());
            u.setRole(roleCombo.getValue());

            utilisateurService.addUtilisateur(u);

            closeDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
