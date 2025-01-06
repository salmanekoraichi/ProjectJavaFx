package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;


import java.awt.event.ActionEvent;
import java.io.IOException;

import static server.projectfinal.Utils.PopupNotification.showError;

/**
 * This code is written by Salmane Koraichi
 **/
public class SecretaireController {

    @FXML
    private Button btnLogout;


    @FXML
    private Button BtnInscription;

    @FXML
    private Button btnEtudiant;


    @FXML
    private BorderPane borderpane;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/projectfinal/Views/etudiant-view.fxml" ));
        Parent view = loader.load();
        borderpane.setCenter(view);
    }

    /**
     * Handles the action when the "Étudiants" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleaficheretudiant(javafx.event.ActionEvent event) {
        loadView("etudiant-view.fxml");
    }

    private void loadetd() {
        // Already on Dashboard, optionally refresh data
        loadView("etudiant-view.fxml");
    }


    /**
     * Handles the action when the "Inscriptions" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleaficherinsctiption(javafx.event.ActionEvent event) {
        loadView("inscription-view.fxml");
    }


    @FXML
    private void out(javafx.event.ActionEvent event) {
        try {
            // Close the current stage
            borderpane.getScene().getWindow().hide();

            // Load the login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/projectfinal/Views/login-view.fxml"));
            Parent loginView = loader.load();

            // Create a new stage for the login screen
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(loginView));
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an alert to the user
            showError("Unable to logout and return to the login screen: " + e.getMessage());
        }
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
            borderpane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an alert to the user
        }
    }

}
