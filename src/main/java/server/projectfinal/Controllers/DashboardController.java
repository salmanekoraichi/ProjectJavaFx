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
public class DashboardController {

    @FXML
    private Label lblTotalEtudiants;

    @FXML
    private Label lblTotalProfesseurs;

    @FXML
    private Label lblTotalModules;

    @FXML
    private Label lblTotalInscriptions;

    @FXML
    private LineChart<String, Number> lineChartDonneesEtudiants;

    @FXML
    private BarChart<String, Number> barChartInscriptionsAnnee;

    @FXML
    private LineChart<String, Number> lineChartModulesProf;

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
     * Handles the action when the "Étudiants" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleEtudiantsAction(ActionEvent event) {
        loadView("EtudiantsView.fxml");
    }

    /**
     * Handles the action when the "Professeurs" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleProfesseursAction(ActionEvent event) {
        loadView("ProfesseursView.fxml");
    }

    /**
     * Handles the action when the "Modules" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleModulesAction(ActionEvent event) {
        loadView("ModulesView.fxml");
    }

    /**
     * Handles the action when the "Inscriptions" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleInscriptionsAction(ActionEvent event) {
        loadView("InscriptionsView.fxml");
    }

    /**
     * Handles the action when the "Se déconnecter" button is clicked.
     *
     * @param event the action event
     */
    @FXML
    private void handleLogoutAction(ActionEvent event) {
        // Implement logout logic, such as returning to the login screen
        loadView("LoginView.fxml");
    }

    /**
     * Loads a new view into the center of the BorderPane.
     *
     * @param fxml the FXML file to load
     */
    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/projectfinal/FXML/" + fxml));
            Parent view = loader.load();
            borderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an alert to the user
        }
    }
}
