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
 * Controller for the "Modifier un module" dialog.
 */
public class ModifierModuleController {

    @FXML
    private TextField LibilleModuleModif;

    @FXML
    private TextField CodeModuleModif;

    @FXML
    private ComboBox<Professeur> ProfesseurModule;

    private ModuleService moduleService;
    private ProfesseurService professeurService;

    private Modul module; // The module we're editing

    @FXML
    private void initialize() {
        // We'll load the combobox from setProfesseurService
    }

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    public void setProfesseurService(ProfesseurService professeurService) {
        this.professeurService = professeurService;
        loadProfesseursIntoCombo();
    }

    private void loadProfesseursIntoCombo() {
        if (professeurService == null) return;
        List<Professeur> profs = professeurService.getAllProfesseurs();
        ProfesseurModule.getItems().setAll(profs);
    }

    /**
     * Called by ModuleController after loading the FXML
     * to pass the selected module data.
     */
    public void setModule(Modul module) {
        this.module = module;
        if (module == null) return;

        LibilleModuleModif.setText(module.getNomModule());
        CodeModuleModif.setText(module.getCodeModule());

        // If we have a professeurId, find that Professeur in the list
        if (professeurService != null) {
            List<Professeur> profs = professeurService.getAllProfesseurs();
            for (Professeur p : profs) {
                if (p.getId() == module.getProfesseurId()) {
                    ProfesseurModule.setValue(p);
                    break;
                }
            }
        }
    }

    @FXML
    private void handleModifierModuleModif() {
        if (module == null) return;

        // Update the module object with new fields
        module.setNomModule(LibilleModuleModif.getText());
        module.setCodeModule(CodeModuleModif.getText());

        Professeur selectedProf = ProfesseurModule.getValue();
        if (selectedProf != null) {
            module.setProfesseurId(selectedProf.getId());
        }

        // Update in DB
        moduleService.updateModule(module);

        // Close
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) LibilleModuleModif.getScene().getWindow();
        stage.close();
    }
}
