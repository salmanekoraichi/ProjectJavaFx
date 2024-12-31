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

    @FXML
    private void initialize() {
        // Possibly fill combos or do it once service is set
    }

    public void setInscriptionsService(InscriptionsService service) {
        this.inscriptionsService = service;
        // load combos if needed
    }

    public void setInscription(Inscription ins) {
        this.inscription = ins;
        if (inscription == null) return;

        // If you have a way to fetch the actual Etudiant object from ID:
        // e.g. inscriptionsService.findEtudiantById( ins.getEtudiantId() ), then set in combo
        // For now just demonstrate
        //etudiantCombo.setValue(...);

        // Similarly for module
        //moduleCombo.setValue(...);

        // And date
        if (ins.getDateInscription() != null) {
            LocalDate date = LocalDate.parse(ins.getDateInscription(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateInscriptionPicker.setValue(date);
        }
    }

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

    private void closeDialog() {
        Stage stage = (Stage) dateInscriptionPicker.getScene().getWindow();
        stage.close();
    }
}
