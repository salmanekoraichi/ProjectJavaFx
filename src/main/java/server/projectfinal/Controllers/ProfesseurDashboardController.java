package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import static server.projectfinal.Utils.PopupNotification.showError;

/**
 * This code is written by Salmane Koraichi
 **/
public class ProfesseurDashboardController {


    @FXML
    private Button EtdBtn;

    @FXML
    private Button MdlBtn;

    @FXML
    private Button logout;

    @FXML
    private BorderPane Borderpane;


    @FXML
    void handleEtd(ActionEvent event) {
        loadView("utilisateur-view.fxml");

    }

    @FXML
    void handleMdl(ActionEvent event) {
        loadView("utilisateur-view.fxml");
    }


    @FXML
    private void handlelogout(ActionEvent event) {
        try {
            // Close the current stage
            logout.getScene().getWindow().hide();

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
            Borderpane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an alert to the user
        }
    }


}
