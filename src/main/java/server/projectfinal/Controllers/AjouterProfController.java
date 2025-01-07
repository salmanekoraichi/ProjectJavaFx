package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.projectfinal.DAO.*;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Models.Professeur;
import server.projectfinal.Services.EtudiantService;
import server.projectfinal.Services.ProfesseurService;

import java.time.format.DateTimeFormatter;


/**
 * This code is written by Salmane Koraichi
 **/
public class AjouterProfController {

    @FXML
    private Button handleaddprofo;

    @FXML
    private TextField nomprofadd;

    @FXML
    private TextField prenomprofadd;

    @FXML
    private TextField specialiteprofadd;


    private final ProfesseurService professeurService;


    public AjouterProfController() {
        // for FXML
        ProfesseurDAO professeurDAO = new ProfesseurDAOImpl();
        this.professeurService = new ProfesseurService(professeurDAO);
        // or set it later with a setter
    }

    /**
     * Called when the FXML is loaded. You can do any initialization here.
     */
    @FXML
    private void initialize() {
        // If needed, do any field initialization
    }

    /**
     * Handle click on "Ajouter" button in the dialog.
     */
    @FXML
    private void ajouter() {
        System.out.println(">>> handleAjouter() was called! <<<");
        try {
            // Create a new Etudiant object from fields
            Professeur Nvprofesseur = new Professeur();
            Nvprofesseur.setNom(nomprofadd.getText());
            Nvprofesseur.setPrenom(prenomprofadd.getText());
            Nvprofesseur.setSpecialite(specialiteprofadd.getText());
            Nvprofesseur.setUsername();

            // Save the new prof via service
            professeurService.addProfesseur(Nvprofesseur);


            // Close the dialog
            closeDialog();

        } catch (Exception e) {
            e.printStackTrace();
            // Show error if desired
        }
    }

    /**
     * Close this dialog stage.
     */
    private void closeDialog() {
        Stage stage = (Stage) handleaddprofo.getScene().getWindow();
        stage.close();
    }

}
