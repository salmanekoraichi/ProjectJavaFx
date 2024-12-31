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
import server.projectfinal.DAO.*;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Services.EtudiantService;
import server.projectfinal.Services.ProfesseurService;
import server.projectfinal.Utils.TableUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Optional;

import static server.projectfinal.Utils.TableUtil.exportToCSV;
import static server.projectfinal.Utils.TableUtil.exportToPDF;

/**
 * This code is written by Salmane Koraichi
 **/

//Manages professor-specific operations.


public class ProfesseurController {

    @FXML
    private AnchorPane SceneContainer;

    @FXML
    private AnchorPane TableContainerprof;

    @FXML
    private Button btnaddprof;

    @FXML
    private Button handlemodifyprof;

    @FXML
    private Button handleremoveprof;

    @FXML
    private Button btnExportCSV, btnExportPDF;

    private final ProfesseurService professeurService;

    private TableView<ObservableList<String>> profTable;

    // Constructeur par défaut (nécessaire pour FXML)
    public ProfesseurController() {
        ProfesseurDAO professeurDAO = new ProfesseurDAOImpl();
        this.professeurService = new ProfesseurService(professeurDAO);
    }

    // Constructeur pour injecter l'instance d'EtudiantService
    public ProfesseurController(ProfesseurService professeurService) {
        this.professeurService = professeurService;
    }

    @FXML
    public void initialize() {
        // Initialisation de la table ou d'autres composants.
        loadprofs();

        btnExportCSV.setOnAction(event -> exportToCSV(profTable, "Professeurs.csv"));
        btnExportPDF.setOnAction(event -> exportToPDF(profTable, "Professeurs.pdf"));

        // Désactiver les boutons de modification et de suppression par défaut.
        handlemodifyprof.setDisable(true);
        handleremoveprof.setDisable(true);

        // Ajouter des écouteurs pour activer/désactiver les boutons en fonction de la sélection.
        // Exemple : Ajouter un listener à la table.
    }

    private void loadprofs() {
        try {
            // Récupérer les données des étudiants sous forme de ResultSet
            ResultSet rs = professeurService.load();

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
            TableContainerprof.getChildren().clear();

            // Créer une nouvelle table en utilisant TableUtil
            profTable = TableUtil.FillTable(rs);

            // Ajouter la table au conteneur
            TableContainerprof.getChildren().add(profTable);

            // Activer/Désactiver les boutons en fonction de la sélection
            profTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                boolean selected = newSelection != null;
                handlemodifyprof.setDisable(!selected);
                handleremoveprof.setDisable(!selected);
            });
        } catch (Exception e) {
            e.printStackTrace();
            // Vous pouvez afficher une alerte ici en cas d'erreur
        }
    }

    @FXML
    private void handleAddprof() {
        try {
            System.out.println("handleAddStudent called!");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/server/projectfinal/Views/dialogs/ajouter-professeur-dialog.fxml")
            );
            System.out.println("prof ola called!");

            Parent root = loader.load();
            Stage stage = new Stage();
            System.out.println("chichi prof called!");

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter Étudiant");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Rafraîchir la table après l'ajout.
            loadprofs();
        } catch (IOException e) {
            e.printStackTrace(); // So we can see the real error in the console
            showError("Erreur lors de l'ouverture du dialogue d'ajout : " + e.getMessage());
        }

    }



    @FXML
    private void handleModifyprof() {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/projectfinal/Views/dialogs/modifier-professeur-dialog.fxml"));
            loader.setControllerFactory(param -> new ModifierEtudiantController(etudiantService));

            Parent root = loader.load();

            ModifierEtudiantController controller = loader.getController();
            controller.setEtudiant(selectedStudent.get());

            Stage stage = new Stage();
            stage.setTitle("Modifier Étudiant");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Rafraîchir les données après modification
            loadprofs();
        } catch (IOException e) {
            showError("Erreur lors de l'ouverture du dialogue de modification : " + e.getMessage());
        }
    }

    @FXML
    private void handleRemoveprof() {
        // Vérifier la sélection d'un étudiant.
        Optional<Etudiant> selectedStudent = getSelectedStudent();
        if (selectedStudent.isEmpty()) {
            showError("Veuillez sélectionner un étudiant à supprimer.");
            return;
        }

        professeurService.deleteProfesseur(selectedStudent.get().getId());
        showSuccess("Étudiant supprimé avec succès !");
        loadprofs();
    }


    private Optional<Etudiant> getSelectedStudent() {
        // 1) Grab the selected row from the table
        ObservableList<String> row = profTable.getSelectionModel().getSelectedItem();
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



}
