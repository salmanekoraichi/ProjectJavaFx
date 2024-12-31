package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Services.EtudiantService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controller for the "Modifier Étudiant" dialog.
 */
public class ModifierEtudiantController {

    @FXML
    private TextField matriculeField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private DatePicker dateNaissanceField;
    @FXML
    private TextField emailField;

    private final EtudiantService etudiantService;
    private Etudiant etudiant; // the student being modified

    // Constructor injection of service
    public ModifierEtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    /**
     * Populate fields with the selected student’s data.
     */
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        if (etudiant == null) return;

        matriculeField.setText(etudiant.getMatricule());
        nomField.setText(etudiant.getNom());
        prenomField.setText(etudiant.getPrenom());

        // If your Etudiant model has a Date, convert it to LocalDate
        if (etudiant.getDateNaissance() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dateNaissanceField.setValue(LocalDate.parse(etudiant.getDateNaissance(), formatter));
        }

        emailField.setText(etudiant.getEmail());
    }

    /**
     * Handle click on "Modifier" button.
     */
    @FXML
    private void handleModifier() {
        if (etudiant == null) return;
        try {
            etudiant.setMatricule(matriculeField.getText());
            etudiant.setNom(nomField.getText());
            etudiant.setPrenom(prenomField.getText());

            if (dateNaissanceField.getValue() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                etudiant.setDateNaissance(dateNaissanceField.getValue().format(formatter));
            }

            etudiant.setEmail(emailField.getText());

            // Update in DB
            etudiantService.updateEtudiant(etudiant);

            // Close dialog
            closeDialog();

        } catch (Exception e) {
            e.printStackTrace();
            // Show error if desired
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) matriculeField.getScene().getWindow();
        stage.close();
    }
}
