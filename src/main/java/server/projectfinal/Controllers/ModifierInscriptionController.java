package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Models.Inscription;
import server.projectfinal.Models.Modul;
import server.projectfinal.Services.EtudiantService;
import server.projectfinal.Services.InscriptionsService;
import server.projectfinal.Services.ModuleService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for the "Modifier une inscription" dialog.
 */
public class ModifierInscriptionController {

    @FXML
    private ComboBox<Etudiant> etudiantCombo;
    @FXML
    private ComboBox<Modul> moduleCombo;
    @FXML
    private DatePicker dateInscriptionPicker;

    private InscriptionsService inscriptionsService;
    private Inscription inscription; // The one being modified
    private EtudiantService etudiantS;
    private ModuleService ms;
    @FXML
    private void initialize() {
        // Initialize the comboboxes with data
        if (inscriptionsService != null) {
            List<Etudiant> etudiants = etudiantS.getAllEtudiants();
            List<Modul> modules = ms.getAllModules();
            etudiantCombo.getItems().setAll(etudiants);
            moduleCombo.getItems().setAll(modules);
        }
    }

    public void setInscription(Inscription ins) {
        this.inscription = ins;
        if (inscription == null) return;

        // Set the selected student and module in the comboboxes
        Etudiant etudiant = etudiantS.getEtudiantById(inscription.getEtudiantId());
        Modul module = ms.getModuleById(inscription.getModuleId());
        etudiantCombo.setValue(etudiant);
        moduleCombo.setValue(module);

        // Set the date in the date picker
        if (inscription.getDateInscription() != null) {
            LocalDate date = LocalDate.parse(inscription.getDateInscription(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateInscriptionPicker.setValue(date);
        }
    }

    /*@FXML
    private void handleModifierInscription() {
        if (inscription == null) return;
        try {
            Etudiant e = etudiantCombo.getValue();
            Modul m = moduleCombo.getValue();
            LocalDate d = dateInscriptionPicker.getValue();

            if (e != null) {
                inscription.setEtudiantId(e.getId());
            }
            if (m != null) {
                inscription.setModuleId(m.getId());
            }
            if (d != null) {
                inscription.setDateInscription(d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            inscriptionsService.updateInscription(inscription);
            closeDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    private void handleModifierInscription() {
        if (inscription == null) return;
        try {
            Etudiant e = etudiantCombo.getValue();
            Modul m = moduleCombo.getValue();
            LocalDate d = dateInscriptionPicker.getValue();

            if (e != null) {
                inscription.setEtudiantId(e.getId());
            }
            if (m != null) {
                inscription.setModuleId(m.getId());
            }
            if (d != null) {
                inscription.setDateInscription(d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            // Now update in DB if you have such a method:
            // inscriptionsService.updateInscription(inscription);

            closeDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initializeComboboxes() {
        if (etudiantS != null && ms != null) {
            List<Etudiant> etudiants = etudiantS.getAllEtudiants();
            List<Modul> modules = ms.getAllModules();
            etudiantCombo.getItems().setAll(etudiants);
            moduleCombo.getItems().setAll(modules);
        }
    }

    public void setInscriptionsService(InscriptionsService service) {
        this.inscriptionsService = service;
    }

    public void setEtudiantService(EtudiantService etudiantService) {
        this.etudiantS = etudiantService;
        initializeComboboxes(); // Load data when the service is set
    }

    public void setModuleService(ModuleService moduleService) {
        this.ms = moduleService;
        initializeComboboxes(); // Load data when the service is set
    }



    private void closeDialog() {
        Stage stage = (Stage) dateInscriptionPicker.getScene().getWindow();
        stage.close();
    }
}
