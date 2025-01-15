package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.projectfinal.Models.Modul;
import server.projectfinal.Models.Professeur;
import server.projectfinal.Services.ModuleService;
import server.projectfinal.Services.ProfesseurService;

import java.util.List;

/**
 * This code is written by Salmane Koraichi
 * Controller for the "Ajouter un module" dialog.
 */
public class AjouterModuleController {

    @FXML
    private TextField LibilleModuleAjout;

    @FXML
    private TextField CodeModuleAjout;

    @FXML
    private ComboBox<Professeur> ProfesseurModuleAjout;

    private ModuleService moduleService; // Corrected type from Modul to ModuleService
    private ProfesseurService professeurService;
    // Called automatically by JavaFX after FXML loads
    @FXML
    private void initialize() {
        // Debugging: Ensure the ComboBox is not null
        if (ProfesseurModuleAjout == null) {
            System.out.println("ProfesseurModuleAjout is null in initialize()");
        }
    }

    // Set the services from outside (ModuleController)
    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    public void setProfesseurService(ProfesseurService professeurService) {
        this.professeurService = professeurService;
        loadProfesseursIntoCombo();
    }

    private void loadProfesseursIntoCombo() {
        if (professeurService == null) {
            System.out.println("ProfesseurService is null in loadProfesseursIntoCombo()");
            return;
        }
        if (ProfesseurModuleAjout == null) {
            System.out.println("ProfesseurModuleAjout is null in loadProfesseursIntoCombo()");
            return;
        }
        List<Professeur> profs = professeurService.getAllProfesseurs();
        System.out.println("Professors loaded: " + profs.size());
        ProfesseurModuleAjout.getItems().setAll(profs);
    }

    @FXML
    private void handleAjouterModule() {
        try {
            // Build the new module
            Modul newModule = new Modul();
            newModule.setNomModule(LibilleModuleAjout.getText());
            newModule.setCodeModule(CodeModuleAjout.getText());

            // Get selected professeur from combo
            Professeur selectedProf = ProfesseurModuleAjout.getValue();
            if (selectedProf != null) {
                newModule.setProfesseurId(selectedProf.getId());
            }

            // Save to DB
            if (moduleService == null) {
                System.out.println("moduleService is null in handleAjouterModule()");
                return;
            }
            moduleService.addModule(newModule);

            // Close dialog
            closeDialog();

        } catch (Exception e) {
            e.printStackTrace();
            // Could show an alert if desired
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) LibilleModuleAjout.getScene().getWindow();
        stage.close();
    }
}