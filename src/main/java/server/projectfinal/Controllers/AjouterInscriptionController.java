package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Models.Inscription;
import server.projectfinal.Models.Modul;
import server.projectfinal.Services.InscriptionsService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for the "Ajouter une inscription" dialog.
 */
public class AjouterInscriptionController {

    @FXML
    private ComboBox<Etudiant> etudiantCombo;
    @FXML
    private ComboBox<Modul> moduleCombo;
    @FXML
    private DatePicker dateInscriptionPicker;

    private InscriptionsService inscriptionsService; // we assume you'll set this from outside

    /**
     * Called after FXML load. You can fill the combos here, or
     * if you prefer, fill them after you setInscriptionsService() from outside.
     */
    @FXML
    private void initialize() {
        // Example:
        //etudiantCombo.getItems().addAll(...some list of Etudiant...);
        //moduleCombo.getItems().addAll(...some list of Module...);
    }

    public void setInscriptionsService(InscriptionsService service) {
        this.inscriptionsService = service;
        // If you want, load data from DB here:
        //   List<Etudiant> allEtudiants = ...
        //   etudiantCombo.getItems().setAll(allEtudiants);
        //   List<Module> allModules = ...
        //   moduleCombo.getItems().setAll(allModules);
    }

    @FXML
    private void handleAjouterInscription() {
        try {
            // Build the Inscription from combos + date
            Inscription i = new Inscription();

            Etudiant selectedEtudiant = etudiantCombo.getValue();
            Modul selectedModule = moduleCombo.getValue();
            LocalDate selectedDate = dateInscriptionPicker.getValue();

            if (selectedEtudiant != null) {
                i.setEtudiantId(selectedEtudiant.getId());
            }
            if (selectedModule != null) {
                i.setModuleId(selectedModule.getId());
            }
            if (selectedDate != null) {
                i.setDateInscription(selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            // Save to DB
            inscriptionsService.addInscription(i);

            closeDialog();
        } catch (Exception e) {
            e.printStackTrace();
            // Show an alert if desired
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) dateInscriptionPicker.getScene().getWindow();
        stage.close();
    }
}
