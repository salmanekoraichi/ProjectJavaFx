package server.projectfinal.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.projectfinal.DAO.InscriptionDAO;
import server.projectfinal.DAO.InscriptionDAOImpl;
import server.projectfinal.Models.Inscription;
import server.projectfinal.Services.InscriptionsService;
import server.projectfinal.Utils.TableUtil;
import static server.projectfinal.Utils.TableUtil.exportToCSV;
import static server.projectfinal.Utils.TableUtil.exportToPDF;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Optional;

/**
 * Manages Inscription table and actions (add/modify/remove).
 */
public class InscriptionController {

    @FXML
    private AnchorPane TableContainer;

    @FXML
    private Button btnAddInscription, btnModifyInscription, btnRemoveInscription;

    @FXML
    private Button btnExportCSV, btnExportPDF;

    @FXML
    private TextField searchFieldInscription;


    // We'll store data in a TableView of string rows (like your approach)
    private TableView<ObservableList<String>> inscriptionTable;

    private final InscriptionsService inscriptionsService;

    public InscriptionController() {
        InscriptionDAO inscriptionDAO = new InscriptionDAOImpl();
        // If you have a no-arg constructor:
        // create the service or inject it somehow
        this.inscriptionsService = new InscriptionsService(inscriptionDAO);
    }

    @FXML
    public void initialize() {
        loadInscriptions();

        // If you want “live search” as user types:
        searchFieldInscription.textProperty().addListener((obs, oldVal, newVal) -> {
            handleSearchInscriptions(newVal);
        });

        btnExportCSV.setOnAction(event -> exportToCSV(inscriptionTable, "Inscriptions.csv"));
        btnExportPDF.setOnAction(event -> exportToPDF(inscriptionTable, "Inscriptions.pdf"));

        // Disable modify/remove until row is selected
        btnModifyInscription.setDisable(true);
        btnRemoveInscription.setDisable(true);
    }

    private void loadInscriptions() {
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

    private void updateTable(ResultSet rs) {
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
                btnModifyInscription.setDisable(!selected);
                btnRemoveInscription.setDisable(!selected);
            });
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors de la construction du tableau: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddInscription() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/server/projectfinal/Views/dialogs/ajouter-inscription-dialog.fxml"
            ));
            Parent root = loader.load();

            AjouterInscriptionController controller = loader.getController();
            // pass the service if needed
            controller.setInscriptionsService(inscriptionsService);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter Inscription");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadInscriptions();
        } catch (IOException e) {
            showError("Erreur lors de l'ouverture de la fenêtre d'ajout: " + e.getMessage());
        }
    }

    @FXML
    private void handleModifyInscription() {
        Optional<Inscription> selected = getSelectedInscription();
        if (selected.isEmpty()) {
            showError("Veuillez sélectionner une inscription à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/server/projectfinal/Views/dialogs/modifier-inscription-dialog.fxml"
            ));
            Parent root = loader.load();

            ModifierInscriptionController controller = loader.getController();
            controller.setInscriptionsService(inscriptionsService);
            controller.setInscription(selected.get());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier Inscription");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadInscriptions();
        } catch (IOException e) {
            showError("Erreur lors de l'ouverture de la fenêtre de modification: " + e.getMessage());
        }
    }

    @FXML
    private void handleRemoveInscription() {
        Optional<Inscription> selected = getSelectedInscription();
        if (selected.isEmpty()) {
            showError("Veuillez sélectionner une inscription à supprimer.");
            return;
        }

        try {
            inscriptionsService.deleteInscription(selected.get().getId());
            showSuccess("Inscription supprimée avec succès!");
            loadInscriptions();
        } catch (Exception e) {
            showError("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    // Convert the selected TableView row into an Inscription object
    private Optional<Inscription> getSelectedInscription() {
        if (inscriptionTable == null) return Optional.empty();
        ObservableList<String> row = inscriptionTable.getSelectionModel().getSelectedItem();
        if (row == null) return Optional.empty();

        try {
            // Suppose your columns are: [ID, etudiantId, moduleId, dateInscription, ...]
            Inscription i = new Inscription();
            i.setId(Integer.parseInt(row.get(0)));
            i.setEtudiantId(Integer.parseInt(row.get(1)));
            i.setModuleId(Integer.parseInt(row.get(2)));
            // dateInscription might be row.get(3) - parse if needed

            return Optional.of(i);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void handleSearchInscriptions(String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                // show all
                loadInscriptions();
                return;
            }
            // We assume your InscriptionsService has searchInscriptions(query)
            ResultSet rs = inscriptionsService.searchInscriptions(query);
            updateTable(rs);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors de la recherche : " + e.getMessage());
        }
    }
}
