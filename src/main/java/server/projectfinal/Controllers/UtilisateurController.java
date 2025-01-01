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
import server.projectfinal.DAO.UtilisateurDAO;
import server.projectfinal.DAO.UtilisateurDAOImpl;
import server.projectfinal.Models.Utilisateur;
import server.projectfinal.Services.UtilisateurService;
import server.projectfinal.Utils.TableUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Optional;

public class UtilisateurController {

    @FXML
    private AnchorPane TableContainer;

    @FXML
    private TextField searchFieldUser;

    @FXML
    private Button btnAddUser, btnModifyUser, btnRemoveUser;

    private TableView<ObservableList<String>> userTable;

    private final UtilisateurService utilisateurService;

    public UtilisateurController() {
        // Similar to how you did in InscriptionController
        // Build or inject your DAO & service
        UtilisateurDAO userDAO = new UtilisateurDAOImpl();
        this.utilisateurService = new UtilisateurService(userDAO);
    }

    @FXML
    public void initialize() {
        loadUsers();

        searchFieldUser.textProperty().addListener((obs, oldVal, newVal) -> {
            handleSearchUsers(newVal);
        });

        // Disable Modify/Remove by default
        btnModifyUser.setDisable(true);
        btnRemoveUser.setDisable(true);
    }

    private void loadUsers() {
        try {
            ResultSet rs = utilisateurService.load();
            updateTable(rs);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors du chargement des utilisateurs : " + e.getMessage());
        }
    }

    private void updateTable(ResultSet rs) {
        try {
            TableContainer.getChildren().clear();
            userTable = TableUtil.FillTable(rs);
            TableContainer.getChildren().add(userTable);

            // Ajuster les ancres pour que la table s'étende correctement
            AnchorPane.setTopAnchor(userTable, 10.0); // Adjust top margin
            AnchorPane.setLeftAnchor(userTable, 10.0); // Adjust left margin
            AnchorPane.setRightAnchor(userTable, 10.0); // Adjust right margin
            AnchorPane.setBottomAnchor(userTable, 10.0); // Optional for bottom margin

            // Listen for selection changes
            userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean selected = (newVal != null);
                btnModifyUser.setDisable(!selected);
                btnRemoveUser.setDisable(!selected);
            });
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors de la construction du tableau Utilisateurs: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/server/projectfinal/Views/dialogs/ajouter-utilisateur-dialog.fxml"
            ));
            Parent root = loader.load();

            // If your add-user dialog has a controller named "AjouterUtilisateurController":
            AjouterUtilisateurController controller = loader.getController();
            controller.setUtilisateurService(utilisateurService);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter Utilisateur");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh
            loadUsers();
        } catch (IOException e) {
            showError("Erreur lors de l'ouverture de la fenêtre d'ajout : " + e.getMessage());
        }
    }

    @FXML
    private void handleModifyUser() {
        Optional<Utilisateur> selectedUser = getSelectedUser();
        if (selectedUser.isEmpty()) {
            showError("Veuillez sélectionner un utilisateur à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/server/projectfinal/Views/dialogs/modifier-utilisateur-dialog.fxml"
            ));
            Parent root = loader.load();

            // Suppose you have "ModifierUtilisateurController":
            ModifierUtilisateurController controller = loader.getController();
            controller.setUtilisateurService(utilisateurService);
            controller.setUtilisateur(selectedUser.get());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier Utilisateur");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadUsers();
        } catch (IOException e) {
            showError("Erreur lors de l'ouverture de la fenêtre de modification : " + e.getMessage());
        }
    }

    @FXML
    private void handleRemoveUser() {
        Optional<Utilisateur> selected = getSelectedUser();
        if (selected.isEmpty()) {
            showError("Veuillez sélectionner un utilisateur à supprimer.");
            return;
        }

        try {
            utilisateurService.deleteUtilisateur(selected.get().getId());
            showSuccess("Utilisateur supprimé avec succès !");
            loadUsers();
        } catch (Exception e) {
            showError("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    /**
     * Convert the selected row in the table to a Utilisateur object.
     * Adjust indexes to match your columns in the DB.
     */
    private Optional<Utilisateur> getSelectedUser() {
        if (userTable == null) return Optional.empty();

        ObservableList<String> row = userTable.getSelectionModel().getSelectedItem();
        if (row == null) return Optional.empty();

        try {
            // Suppose columns are: [ID, USERNAME, PASSWORD, ROLE, ...]
            // Adjust as needed
            Utilisateur u = new Utilisateur();
            u.setId(Integer.parseInt(row.get(0)));
            u.setUsername(row.get(1));
            u.setPassword(row.get(2));
            u.setRole(row.get(3));

            return Optional.of(u);
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

    private void handleSearchUsers(String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                loadUsers();
                return;
            }
            ResultSet rs = utilisateurService.searchUtilisateurs(query);
            updateTable(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
