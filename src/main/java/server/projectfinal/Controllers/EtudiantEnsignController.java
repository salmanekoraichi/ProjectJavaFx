package server.projectfinal.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import server.projectfinal.DAO.ModuleDAO;
import server.projectfinal.DAO.ModuleDAOImpl;
import server.projectfinal.DAO.ProfesseurDAO;
import server.projectfinal.DAO.ProfesseurDAOImpl;
import server.projectfinal.Services.ModuleService;
import server.projectfinal.Services.ProfesseurService;
import server.projectfinal.Utils.TableUtil;

import java.sql.ResultSet;

import static server.projectfinal.Utils.PopupNotification.showError;

/**
 * This code is written by Salmane Koraichi
 **/
public class EtudiantEnsignController {



    private String username;

    @FXML
    private AnchorPane TableContainer;

    private TableView<ObservableList<String>> etudiant_table;

    private final ModuleService moduleService;
    private final server.projectfinal.Services.ProfesseurService ProfesseurService;

    public EtudiantEnsignController() {
        ModuleDAO moduleDAO = new ModuleDAOImpl();
        this.moduleService = new ModuleService(moduleDAO);

        ProfesseurDAO professeurDAO = new ProfesseurDAOImpl();
        this.ProfesseurService = new ProfesseurService(professeurDAO);
    }

    @FXML
    public void initialize() {

    }

    /*private void loadmodules() {
        if (username == null) {
            showError("Username is not set properly.");
            return;
        }

        // Fetch ResultSet from service method using the username's associated professor ID
        int professeurId = ProfesseurService.findidbyusername(username);

        ResultSet rs = ProfesseurService.getMdsbyid(professeurId);

        if (rs != null) {
            updateTable(rs);
        } else {
            showError("No modules found for this professor.");
        }
    }*/

    private void loadetudiant() {
        if (username == null) {
            showError("Username is not set properly.");
            return;
        }
        System.out.println("loadetudiant callini");
        int professeurId = ProfesseurService.findidbyusername(username);
        ResultSet rs = ProfesseurService.GetEtudiantsById(professeurId);
        System.out.println("loadetudiant sir 3la lah");
        if (rs == null) {
            showError("No modules found for this professor.");
            return;
        }

        updateTable(rs);
    }


    private void updateTable(ResultSet rs) {
        try {
            TableContainer.getChildren().clear();
            etudiant_table = TableUtil.FillTable(rs);
            TableContainer.getChildren().add(etudiant_table);

            // Adjust anchors for table to expand properly
            AnchorPane.setTopAnchor(etudiant_table, 10.0);
            AnchorPane.setLeftAnchor(etudiant_table, 10.0);
            AnchorPane.setRightAnchor(etudiant_table, 10.0);
            AnchorPane.setBottomAnchor(etudiant_table, 10.0);

            etudiant_table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean selected = (newVal != null);
            });
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error constructing the table: " + e.getMessage());
        }
    }

    // Create a method to set the username in the controller
    public void setUsernameetd(String username) {
        this.username = username;
        System.out.println("Logged in aaaaas: " + username);
        loadetudiant(); // Call loadmodules here to update the table
    }
}
