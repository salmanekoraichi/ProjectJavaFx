package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.projectfinal.Models.Utilisateur;
import server.projectfinal.Services.UtilisateurService;

public class ModifierUtilisateurController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ComboBox<String> roleCombo;

    private UtilisateurService utilisateurService;
    private Utilisateur utilisateur;

    @FXML
    private void initialize() {
        // Fill possible roles
        roleCombo.getItems().addAll("admin", "professor", "student", "manager", "secretary");
    }

    public void setUtilisateurService(UtilisateurService service) {
        this.utilisateurService = service;
    }

    public void setUtilisateur(Utilisateur u) {
        this.utilisateur = u;
        if (u == null) return;

        usernameField.setText(u.getUsername());
        passwordField.setText(u.getPassword());
        roleCombo.setValue(u.getRole());
    }

    @FXML
    private void handleModifyUser() {
        if (utilisateur == null) return;
        try {
            utilisateur.setUsername(usernameField.getText());
            utilisateur.setPassword(passwordField.getText());
            utilisateur.setRole(roleCombo.getValue());

            utilisateurService.updateUtilisateur(utilisateur);

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
