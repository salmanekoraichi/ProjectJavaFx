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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.projectfinal.DAO.EtudiantDAO;
import server.projectfinal.DAO.EtudiantDAOImpl;
import server.projectfinal.DAO.InscriptionDAO;
import server.projectfinal.DAO.InscriptionDAOImpl;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Services.EtudiantService;
import server.projectfinal.Utils.TableUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;

import static server.projectfinal.Utils.TableUtil.exportToCSV;
import static server.projectfinal.Utils.TableUtil.exportToPDF;

/**
 * This code is written by Salmane Koraichi
 **/

//Handles student management UI logic.

public class EtudiantController {

    @FXML
    private AnchorPane TableContainer;

    @FXML
    private Button btnAdd;


    @FXML
    private Button handlemodifystudent, handleremovestudent;

    @FXML
    private Button btnExportCSV, btnExportPDF;

    @FXML
    private TextField searchFieldEtudiant;

    private final EtudiantService etudiantService;
    private TableView<ObservableList<String>> studentTable;


    // Constructeur par défaut (nécessaire pour FXML)
    public EtudiantController() {
        EtudiantDAO etudiantDAO = new EtudiantDAOImpl();
        InscriptionDAO inscriptionDAO = new InscriptionDAOImpl();
        this.etudiantService = new EtudiantService(etudiantDAO, inscriptionDAO);
    }

    // Constructeur pour injecter l'instance d'EtudiantService
    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @FXML
    public void initialize() {
        // Initialisation de la table ou d'autres composants.
        loadStudents();

        searchFieldEtudiant.textProperty().addListener((obs, oldV, newV) -> {
            handleSearchEtudiant(newV);
        });


        btnExportCSV.setOnAction(event -> exportToCSV(studentTable, "Etudiants.csv"));
        btnExportPDF.setOnAction(event -> exportToPDF(studentTable, "Etudiants.pdf"));

        // Désactiver les boutons de modification et de suppression par défaut.
        handlemodifystudent.setDisable(true);
        handleremovestudent.setDisable(true);

        // Ajouter des écouteurs pour activer/désactiver les boutons en fonction de la sélection.
        // Exemple : Ajouter un listener à la table.
    }

    private void loadStudents() {
        try {
            // Récupérer les données des étudiants sous forme de ResultSet
            ResultSet rs = etudiantService.load();

            // Mettre à jour la table avec les données du ResultSet
            updateTable(rs);
        } catch (Exception e) {
            e.printStackTrace();
            // Vous pouvez afficher une alerte ici en cas d'erreur
        }
    }

    private void updateTable(ResultSet rs) {
        try {
            // Effacer les enfants existants de TableContainer
            TableContainer.getChildren().clear();

            // Créer une nouvelle table en utilisant TableUtil
            studentTable = TableUtil.FillTable(rs);

            // Ajouter la table au conteneur
            TableContainer.getChildren().add(studentTable);

            // Ajuster les ancres pour que la table s'étende correctement
            AnchorPane.setTopAnchor(studentTable, 10.0); // Adjust top margin
            AnchorPane.setLeftAnchor(studentTable, 10.0); // Adjust left margin
            AnchorPane.setRightAnchor(studentTable, 10.0); // Adjust right margin
            AnchorPane.setBottomAnchor(studentTable, 10.0); // Optional for bottom margin

            // Activer/Désactiver les boutons en fonction de la sélection
            studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                boolean selected = newSelection != null;
                handlemodifystudent.setDisable(!selected);
                handleremovestudent.setDisable(!selected);
            });
        } catch (Exception e) {
            e.printStackTrace();
            // Vous pouvez afficher une alerte ici en cas d'erreur
        }
    }

    @FXML
    private void handleAddStudent() {
        try {
            System.out.println("handleAddStudent called!");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/server/projectfinal/Views/dialogs/ajouter-etudiant-dialog.fxml")
            );
            System.out.println("hola called!");

            Parent root = loader.load();
            Stage stage = new Stage();
            System.out.println("chichi called!");

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter Étudiant");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Rafraîchir la table après l'ajout.
            loadStudents();
        } catch (IOException e) {
            e.printStackTrace(); // So we can see the real error in the console
            showError("Erreur lors de l'ouverture du dialogue d'ajout : " + e.getMessage());
        }

    }

    @FXML
    private void handleModifyStudent() {
        Optional<Etudiant> selectedStudent = getSelectedStudent();
        if (selectedStudent.isEmpty()) {
            showError("Veuillez sélectionner un étudiant à modifier.");
            return;
        }

        try {
            // Créez les DAO nécessaires
            EtudiantDAO etudiantDAO = new EtudiantDAOImpl();
            InscriptionDAO inscriptionDAO = new InscriptionDAOImpl();

            // Créez l'instance du service
            EtudiantService etudiantService = new EtudiantService(etudiantDAO, inscriptionDAO);

            // Configurez le FXMLLoader pour passer le service
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/projectfinal/Views/dialogs/modifier-etudiant-dialog.fxml"));
            loader.setControllerFactory(param -> new ModifierEtudiantController(etudiantService));

            Parent root = loader.load();

            ModifierEtudiantController controller = loader.getController();
            controller.setEtudiant(selectedStudent.get());

            Stage stage = new Stage();
            stage.setTitle("Modifier Étudiant");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Rafraîchir les données après modification
            loadStudents();
        } catch (IOException e) {
            showError("Erreur lors de l'ouverture du dialogue de modification : " + e.getMessage());
        }
    }


    @FXML
    private void handleRemoveStudent() {
        // Vérifier la sélection d'un étudiant.
        Optional<Etudiant> selectedStudent = getSelectedStudent();
        if (selectedStudent.isEmpty()) {
            showError("Veuillez sélectionner un étudiant à supprimer.");
            return;
        }

        etudiantService.deleteEtudiant(selectedStudent.get().getId());
        showSuccess("Étudiant supprimé avec succès !");
        loadStudents();
    }

    private Optional<Etudiant> getSelectedStudent() {
        // 1) Grab the selected row from the table
        ObservableList<String> row = studentTable.getSelectionModel().getSelectedItem();
        if (row == null) {
            return Optional.empty(); // nothing selected
        }

        try {
            // 2) Build an Etudiant from the columns
            Etudiant e = new Etudiant();
            e.setId(Integer.parseInt(row.get(0)));     // parse the first column for ID
            e.setMatricule(row.get(1));
            e.setNom(row.get(2));
            e.setPrenom(row.get(3));
            e.setDateNaissance(row.get(4));
            e.setEmail(row.get(5));
            e.setPromotion(row.get(6));

            return Optional.of(e);

        } catch (Exception ex) {
            ex.printStackTrace();
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

    private void handleSearchEtudiant(String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                loadStudents(); // revert to all
                return;
            }
            ResultSet rs = etudiantService.searchEtudiants(query);
            updateTable(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
