package server.projectfinal.Controllers;

/**
 * This code is written by Salmane Koraichi
 **/
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.projectfinal.Models.Utilisateur;
import server.projectfinal.Services.UtilisateurService;

import java.io.IOException;

public class LoginView {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final AuthController authController;

    public LoginView() {
        UtilisateurService utilisateurService = new UtilisateurService();
        this.authController = new AuthController(utilisateurService);
    }

    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Utilisateur utilisateur = authController.login(username, password);
        if (utilisateur != null) {
            redirectToDashboard(utilisateur.getRole());
        } else {
            showErrorAlert("Login failed", "Invalid username or password.");
        }
    }

    private void redirectToDashboard(String role) {
        // Determine the dashboard path based on the role
        String dashboardPath = switch (role) {
            case "admin" -> "/server/projectfinal/Views/admin-view.fxml";
            case "professor" -> "/server/projectfinal/Views/professeur-view.fxml";
            case "student" -> "/server/projectfinal/Views/etudiant-view.fxml";
            case "secretary" -> "/server/projectfinal/Views/secretaire-view.fxml";
            default -> null;
        };

        if (dashboardPath != null) {
            try {
                Stage stage = (Stage) usernameField.getScene().getWindow();
                // Load the FXML file for the dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource(dashboardPath));
                Parent root = loader.load();

                // Set the new scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                showErrorAlert("Error", "Failed to load dashboard for role: " + role);
                e.printStackTrace();
            }
        } else {
            showErrorAlert("Error", "Unknown role: " + role);
        }
    }


    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

