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

public class ProfesseurDashboardController {

    private String username;

    @FXML
    private Button EtdBtn;

    @FXML
    private Button MdlBtn;

    @FXML
    private Button logout;

    @FXML
    private BorderPane Borderpane;

    public ProfesseurDashboardController() {}

    @FXML
    private void initialize() {
        System.out.println("username = " + username);
    }

    @FXML
    void handleEtd(ActionEvent event) {
        loadView("etudiant-enseigne-view.fxml");
    }

    @FXML
    private void handlelogout(ActionEvent event) {
        try {
            logout.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/projectfinal/Views/login-view.fxml"));
            Parent loginView = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Linkey | Connexion");
            loginStage.setScene(new Scene(loginView));
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Unable to logout and return to the login screen: " + e.getMessage());
        }

        System.out.println("Log out username = " + username);
    }

    @FXML
    private void handleMdl(ActionEvent event) {
        loadView("module-enseigne-view.fxml");
    }

    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/projectfinal/Views/" + fxml));
            Parent view = loader.load();

            // Correctly retrieve the controller based on the fxml
            if (fxml.equals("module-enseigne-view.fxml")) {
                ModuleEnsignController controller = loader.getController();
                controller.setUsername(username);
            } else if (fxml.equals("utilisateur-view.fxml")) {
                EtudiantEnsignController controller = loader.getController();
                controller.setUsername(username);
            }
            // Set the new view in the BorderPane
            Borderpane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create a method to set the username in the controller
    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: " + username);
    }
}
