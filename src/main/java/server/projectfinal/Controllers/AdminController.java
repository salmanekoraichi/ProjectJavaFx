package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import static server.projectfinal.Utils.PopupNotification.showError;


/**
 * Controller class for the Dashboard view.
 * Handles initialization and user interactions.
 */
public class AdminController {

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnEtudiants;

    @FXML
    private Button btnProfesseurs;

    @FXML
    private Button btnModules;

    @FXML
    private Button btnInscriptions;

    @FXML
    private Button btnLogout;

    @FXML
    private BorderPane borderPane;
    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        loaddashbord();
    }


    /**
     * Handles the action when the "Tableau de bord" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleDashboardAction(ActionEvent event) {
        // Already on Dashboard, optionally refresh data
        loadView("dashboard-view.fxml");
    }

    private void loaddashbord() {
        // Already on Dashboard, optionally refresh data
        loadView("dashboard-view.fxml");
    }

    /**
     * Handles the action when the "Étudiants" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleEtudiantsAction(ActionEvent event) {
        loadView("etudiant-view.fxml");
    }


    /**
     * Handles the action when the "Inscriptions" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleInscriptionsAction(ActionEvent event) {
        loadView("inscription-view.fxml");
    }


    /**
     * Handles the action when the "Professeurs" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleProfesseursAction(ActionEvent event) {
        loadView("professeur-view.fxml");
    }

    /**
     * Handles the action when the "Modules" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleModulesAction(ActionEvent event) {
        loadView("module-view.fxml");
    }


    /**
     * Handles the action when the "Se déconnecter" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleLogoutAction(ActionEvent event) {
        try {
            // Close the current stage
            btnLogout.getScene().getWindow().hide();

            // Load the login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/projectfinal/Views/login-view.fxml"));
            Parent loginView = loader.load();

            // Create a new stage for the login screen
            Stage loginStage = new Stage();
            loginStage.setTitle("Linkey | Connexion");

            // Set the icon of the window
            Image ensat_image = new Image(getClass().getResource("/server/projectfinal/images/ensalogo.png").toString());
            loginStage.getIcons().add(ensat_image);

            loginStage.setScene(new Scene(loginView));
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an alert to the user
            showError("Unable to logout and return to the login screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleUtilisateurAction(ActionEvent event) {
        // Implement logout logic, such as returning to the login screen
        loadView("utilisateur-view.fxml");
    }

    @FXML
    private void handleAiAction(ActionEvent event) {
        loadView("Aiview.fxml");
    }


    /**
     * Loads a new view into the center of the BorderPane.
     *
     * @param fxml the FXML file to load
     */
    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/projectfinal/Views/" + fxml));
            Parent view = loader.load();
            borderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an alert to the user
        }
    }

}
