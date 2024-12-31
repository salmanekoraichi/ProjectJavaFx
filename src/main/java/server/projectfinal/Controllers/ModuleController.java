package server.projectfinal.Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.projectfinal.DAO.ModuleDAO;
import server.projectfinal.DAO.ModuleDAOImpl;
import server.projectfinal.DAO.ProfesseurDAO;
import server.projectfinal.DAO.ProfesseurDAOImpl;
import server.projectfinal.Models.Modul;
import server.projectfinal.Services.ModuleService;
import server.projectfinal.Services.ProfesseurService;
import server.projectfinal.Utils.TableUtil; // Suppose you have a utility class for building tables from a ResultSet

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Optional;


/**
 * This code is written by Salmane Koraichi
 **/

//Deals with module management in the UI.

public class ModuleController {
    @FXML
    private Button BtnAddModule , BtnModifyModule, BtnRemoveModule;

    @FXML
    private AnchorPane SceneContainer;

    @FXML
    private AnchorPane TableContainerModule;

    // We'll display modules in a TableView<ObservableList<String>> (similar to your approach for Etudiant).
    private TableView<ObservableList<String>> moduleTable;

    // Example services (adapt to your real code):
    private final ModuleService moduleService;
    private final ProfesseurService professeurService;

    // If using FXML default constructor:
    public ModuleController() {
        ModuleDAO moduleDAO = new ModuleDAOImpl();
        ProfesseurDAO professeurDAO = new ProfesseurDAOImpl();
        // Example: create services or fetch from DI
        this.moduleService = new ModuleService(moduleDAO);
        this.professeurService = new ProfesseurService(professeurDAO);
    }

    @FXML
    public void initialize() {
        loadModules();

        // Initially disable modify/remove
        BtnModifyModule.setDisable(true);
        BtnRemoveModule.setDisable(true);
    }

    private void loadModules() {
        try {
            // Suppose your service returns a ResultSet of all modules
            ResultSet rs = moduleService.loadAllModules();
            updateTable(rs);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors du chargement des modules: " + e.getMessage());
        }
    }

    private void updateTable(ResultSet rs) {
        try {
            TableContainerModule.getChildren().clear();
            // Use your existing TableUtil to build a TableView
            moduleTable = TableUtil.FillTable(rs);
            TableContainerModule.getChildren().add(moduleTable);

            // Listen for selection changes
            moduleTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean selected = (newVal != null);
                BtnModifyModule.setDisable(!selected);
                BtnRemoveModule.setDisable(!selected);
            });
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors de la construction de la table module: " + e.getMessage());
        }
    }

    @FXML
    void handleAddModule() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/server/projectfinal/Views/dialogs/ajouter-module-dialog.fxml" // adjust path if needed
            ));

            // Show the dialog
            Parent root = loader.load();
            AjouterModuleController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter un module");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh after user adds
            loadModules();
        } catch (IOException e) {
            showError("Erreur lors de l'ouverture du dialogue d'ajout: " + e.getMessage());
        }
    }

    @FXML
    void handleModifyModule() {
        // Build a Module object from the selected row:
        Optional<Modul> selected = getSelectedModule();
        if (selected.isEmpty()) {
            showError("Veuillez sélectionner un module à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/server/projectfinal/Views/dialogs/modifier-module-dialog.fxml"
            ));
            Parent root = loader.load();

            ModifierModuleController controller = loader.getController();
            controller.setModuleService(moduleService);
            controller.setProfesseurService(professeurService);

            // Pass the selected module to the dialog
            controller.setModule(selected.get());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier un module");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh
            loadModules();
        } catch (IOException e) {
            showError("Erreur lors de l'ouverture du dialogue de modification: " + e.getMessage());
        }
    }

    @FXML
    void handleRemoveModule() {
        Optional<Modul> selected = getSelectedModule();
        if (selected.isEmpty()) {
            showError("Veuillez sélectionner un module à supprimer.");
            return;
        }

        try {
            moduleService.deleteModule(selected.get().getId());
            showSuccess("Module supprimé avec succès!");
            loadModules();
        } catch (Exception e) {
            showError("Erreur lors de la suppression du module: " + e.getMessage());
        }
    }

    /**
     * Converts the selected row in moduleTable into a Module object.
     */
    private Optional<Modul> getSelectedModule() {
        if (moduleTable == null) return Optional.empty();
        ObservableList<String> row = moduleTable.getSelectionModel().getSelectedItem();
        if (row == null) return Optional.empty();

        try {
            // Suppose your columns are: [ ID, LIBELLE, CODE, PROFESSEUR_ID, PROFESSEUR_NAME, ETC... ]
            Modul m = new Modul();

            m.setId(Integer.parseInt(row.get(0)));
            m.setNomModule(row.get(1));
            m.setCodeModule(row.get(2));

            // If you store Prof ID in row[3], you can store or retrieve it here
            int profId = Integer.parseInt(row.get(3));
            m.setProfesseurId(profId);

            return Optional.of(m);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
