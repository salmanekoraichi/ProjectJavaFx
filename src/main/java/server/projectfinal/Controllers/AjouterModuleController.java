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

    private ModuleService moduleService;
    private ProfesseurService professeurService;

    // Called automatically by JavaFX after FXML loads
    @FXML
    private void initialize() {
        // We'll fill the combobox later once we have services
    }

    // Set the services from outside (ModuleController)
    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
        // Possibly load combos here if not done in code
    }

    public void setProfesseurService(ProfesseurService professeurService) {
        this.professeurService = professeurService;
        loadProfesseursIntoCombo();
    }

    private void loadProfesseursIntoCombo() {
        if (professeurService == null) return;
        List<Professeur> profs = professeurService.getAllProfesseurs();
        // Populate combo
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
