package server.projectfinal.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import server.projectfinal.DAO.*;
import server.projectfinal.Services.EtudiantService;
import server.projectfinal.Services.InscriptionsService;
import server.projectfinal.Services.ModuleService;
import server.projectfinal.Utils.TableUtil;

import java.sql.ResultSet;

import static server.projectfinal.Utils.PopupNotification.showError;
import static server.projectfinal.Utils.TableUtil.exportToCSV;
import static server.projectfinal.Utils.TableUtil.exportToPDF;

/**
 * This code is written by Salmane Koraichi
 **/
public class ModuleEnsignController {


    @FXML
    private AnchorPane TableContainer;

    private TableView<ObservableList<String>> ModuleTable;

    private final ModuleService moduleService;

    public ModuleEnsignController() {
        ModuleDAO moduleDAO = new ModuleDAOImpl();
        this.moduleService = new ModuleService(moduleDAO);
    }

    @FXML
    public void initialize() {
/*
        loadInscriptions();
*/
    }

   /* private void loadInscriptions() {
        try {
            // Suppose your InscriptionsService can return a ResultSet for all inscriptions
            ResultSet rs = inscriptionsService.load();
            // We'll convert that to a TableView:
            updateTable(rs);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors du chargement des inscriptions: " + e.getMessage());
        }
    }
*/
   /* private void updateTable(ResultSet rs) {
        try {
            TableContainer.getChildren().clear();

            inscriptionTable = TableUtil.FillTable(rs);
            TableContainer.getChildren().add(inscriptionTable);

            // Ajuster les ancres pour que la table s'étende correctement
            AnchorPane.setTopAnchor(inscriptionTable, 10.0); // Adjust top margin
            AnchorPane.setLeftAnchor(inscriptionTable, 10.0); // Adjust left margin
            AnchorPane.setRightAnchor(inscriptionTable, 10.0); // Adjust right margin
            AnchorPane.setBottomAnchor(inscriptionTable, 10.0); // Optional for bottom margin


            inscriptionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean selected = (newVal != null);
            });
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors de la construction du tableau: " + e.getMessage());
        }
    }*/
}
