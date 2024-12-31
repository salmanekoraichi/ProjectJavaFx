package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import server.projectfinal.Services.DashboardService;
import server.projectfinal.Services.EtudiantService;
import server.projectfinal.Services.ProfesseurService;
import server.projectfinal.Services.InscriptionsService;
import server.projectfinal.DAO.EtudiantDAO;
import server.projectfinal.DAO.EtudiantDAOImpl;
import server.projectfinal.DAO.ProfesseurDAO;
import server.projectfinal.DAO.ProfesseurDAOImpl;
import server.projectfinal.DAO.InscriptionDAO;
import server.projectfinal.DAO.InscriptionDAOImpl;

import java.io.IOException;
import java.util.Map;

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
     * Handles the action when the "Inscriptions" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleInscriptionsAction(ActionEvent event) {
        loadView("inscription-view.fxml");
    }

    /**
     * Handles the action when the "Se déconnecter" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleLogoutAction(ActionEvent event) {
        // Implement logout logic, such as returning to the login screen
        loadView("login-view.fxml");
    }

    @FXML
    private void handleUtilisateurAction(ActionEvent event) {
        // Implement logout logic, such as returning to the login screen
        loadView("utilisateur-view.fxml");
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
