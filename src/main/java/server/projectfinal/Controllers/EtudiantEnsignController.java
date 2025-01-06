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

    private TableView<ObservableList<String>> ModuleTable;

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

    private void loadmodules() {
        if (username == null) {
            showError("Username is not set properly.");
            return;
        }

        int professeurId = ProfesseurService.findidbyusername(username);
        ResultSet rs = ProfesseurService.getModsbyid(professeurId);

        if (rs == null) {
            showError("No modules found for this professor.");
            return;
        }

        updateTable(rs);
    }


    private void updateTable(ResultSet rs) {
        try {
            TableContainer.getChildren().clear();
            ModuleTable = TableUtil.FilloTable(rs);
            TableContainer.getChildren().add(ModuleTable);

            // Adjust anchors for table to expand properly
            AnchorPane.setTopAnchor(ModuleTable, 10.0);
            AnchorPane.setLeftAnchor(ModuleTable, 10.0);
            AnchorPane.setRightAnchor(ModuleTable, 10.0);
            AnchorPane.setBottomAnchor(ModuleTable, 10.0);

            ModuleTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean selected = (newVal != null);
            });
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error constructing the table: " + e.getMessage());
        }
    }

    // Create a method to set the username in the controller
    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: " + username);
        loadmodules(); // Call loadmodules here to update the table
    }
}
