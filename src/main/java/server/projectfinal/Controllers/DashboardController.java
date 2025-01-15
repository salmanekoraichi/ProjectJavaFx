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
import server.projectfinal.DAO.*;
import server.projectfinal.Services.DashboardService;
import server.projectfinal.Services.EtudiantService;
import server.projectfinal.Services.InscriptionsService;
import server.projectfinal.Services.ProfesseurService;

import java.util.Map;

/**
 * This code is written by Salmane Koraichi
 **/
public class DashboardController {
    @FXML
    private AnchorPane SceneContainer;

    @FXML
    private BarChart<String, Number> barChartInscriptionsAnnee;

    @FXML
    private Label lblTotalEtudiants;

    @FXML
    private Label lblTotalInscriptions;

    @FXML
    private Label lblTotalModules;

    @FXML
    private Label lblTotalProfesseurs;

    @FXML
    private LineChart<String, Number> lineChartDonneesEtudiants;

    @FXML
    private LineChart<String, Number> lineChartModulesProf;



    private DashboardService dashboardService;
    private EtudiantService etudiantService;
    private ProfesseurService professeurService;
    private InscriptionsService inscriptionsService;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize DAO implementations
        EtudiantDAO etudiantDAO = new EtudiantDAOImpl();
        InscriptionDAO inscriptionDAO = new InscriptionDAOImpl();
        ProfesseurDAO professeurDAO = new ProfesseurDAOImpl();

        // Initialize services with DAO instances
        this.etudiantService = new EtudiantService(etudiantDAO, inscriptionDAO);
        this.professeurService = new ProfesseurService(professeurDAO);
        this.inscriptionsService = new InscriptionsService(inscriptionDAO);
        this.dashboardService = new DashboardService(etudiantService, professeurService, inscriptionsService);

        // Load data into dashboard
        loadDashboardData();
    }

    /**
     * Loads data into the dashboard labels and charts.
     */
    private void loadDashboardData() {
        // Load Total Counts
        lblTotalEtudiants.setText(String.valueOf(dashboardService.getTotalEtudiants()));
        lblTotalProfesseurs.setText(String.valueOf(dashboardService.getTotalProfesseurs()));
        lblTotalModules.setText(String.valueOf(dashboardService.getTotalModules()));
        lblTotalInscriptions.setText(String.valueOf(dashboardService.getTotalInscriptions()));

        // Load Charts
        loadDonneesEtudiantsChart();
        loadInscriptionsAnneeChart();
        loadModulesProfChart();
    }

    /**
     * Populates the "Données Étudiants" LineChart with relevant data.
     */
    private void loadDonneesEtudiantsChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Étudiants par Promotion");

        Map<String, Integer> data = dashboardService.getEtudiantsByPromotion();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        lineChartDonneesEtudiants.getData().clear();
        lineChartDonneesEtudiants.getData().add(series);
    }

    /**
     * Populates the "Inscriptions / Année" BarChart with relevant data.
     */
    private void loadInscriptionsAnneeChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Inscriptions par Année");

        Map<String, Integer> data = dashboardService.getInscriptionsByYear();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChartInscriptionsAnnee.getData().clear();
        barChartInscriptionsAnnee.getData().add(series);
    }

    /**
     * Populates the "Modules Enseignés par Prof" LineChart with relevant data.
     */
    private void loadModulesProfChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Modules par Professeur");

        Map<String, Integer> data = dashboardService.getModulesByProfesseur();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        lineChartModulesProf.getData().clear();
        lineChartModulesProf.getData().add(series);
    }

    /**
     * Handles the action when the "Tableau de bord" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleDashboardAction(ActionEvent event) {
        // Already on Dashboard, optionally refresh data
        loadDashboardData();
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
            System.err.println("Failed to load view: " + fxml);
        }
    }

    @FXML
    private void handleEtudiantsAction(ActionEvent event) {
        loadView("etudiant-view.fxml");
    }

    @FXML
    private void handleProfesseursAction(ActionEvent event) {
        loadView("professeur-view.fxml");
    }

    @FXML
    private void handleModulesAction(ActionEvent event) {
        loadView("module-view.fxml");
    }

    @FXML
    private void handleInscriptionsAction(ActionEvent event) {
        loadView("dialog/inscription-view.fxml");
    }

    @FXML
    private void handleLogoutAction(ActionEvent event) {
        // Go back to login screen
        loadView("login-view.fxml");
    }


  
}
