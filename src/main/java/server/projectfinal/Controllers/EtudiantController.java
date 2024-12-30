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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import server.projectfinal.DAO.*;
import server.projectfinal.Services.EtudiantService;


import server.projectfinal.Services.EtudiantService;
import server.projectfinal.DAO.EtudiantDAO;
import server.projectfinal.DAO.EtudiantDAOImpl;

/**
 * This code is written by Salmane Koraichi
 **/

//Handles student management UI logic.

public class EtudiantController {
    @FXML
    private AnchorPane TableContainer;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnEtudiants;

    @FXML
    private Button btnInscriptions;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnModules;

    @FXML
    private Button btnProfesseurs;

    @FXML
    private Button btnUtilisateurs;

    @FXML
    void handleDashboardAction(ActionEvent event) {

    }

    @FXML
    void handleEtudiantsAction(ActionEvent event) {

    }

    @FXML
    void handleInscriptionsAction(ActionEvent event) {

    }

    @FXML
    void handleLogoutAction(ActionEvent event) {

    }

    @FXML
    void handleModulesAction(ActionEvent event) {

    }

    @FXML
    void handleProfesseursAction(ActionEvent event) {

    }

    private EtudiantService etudiantService;


    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize DAO implementations
        EtudiantDAO etudiantDAO = new EtudiantDAOImpl();
        InscriptionDAO inscriptionDAO = new InscriptionDAOImpl();

        // Initialize services with DAO instances
        this.etudiantService = new EtudiantService(etudiantDAO, inscriptionDAO);

    }




}
