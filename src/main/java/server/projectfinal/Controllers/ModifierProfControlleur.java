package server.projectfinal.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.projectfinal.DAO.ProfesseurDAO;
import server.projectfinal.DAO.ProfesseurDAOImpl;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Models.Professeur;
import server.projectfinal.Services.EtudiantService;
import server.projectfinal.Services.ProfesseurService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * This code is written by Salmane Koraichi
 **/
public class ModifierProfControlleur {


    @FXML
    private TextField Nomprofmofid;

    @FXML
    private TextField Prenomprofmofid;

    @FXML
    private TextField specialiteprofmofid;

    private final ProfesseurService professeurService;
    private Professeur professeur;

    // Constructor injection of service
    public ModifierProfControlleur() {
        ProfesseurDAO professeurDAO = new ProfesseurDAOImpl();
        ProfesseurService professeurService =new ProfesseurService(professeurDAO);
        this.professeurService = professeurService;
    }

    // Constructor injection of service
    public ModifierProfControlleur(ProfesseurService professeurService) {
        this.professeurService = professeurService;
    }

    /**
     * Populate fields with the selected student’s data.
     */
    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
        if (professeur == null) return;

        Nomprofmofid.setText(professeur.getNom());
        Prenomprofmofid.setText(professeur.getPrenom());
        specialiteprofmofid.setText(professeur.getSpecialite());

    }


    @FXML
    void handleModifierprof() {
        if (professeur == null) return;
        try {
            professeur.setNom(Nomprofmofid.getText());
            professeur.setPrenom(Prenomprofmofid.getText());
            specialiteprofmofid.setText(professeur.getSpecialite());

            // Update in DB
            professeurService.updateProfesseur(professeur);

            // Close dialog
            closeDialog();

        } catch (Exception e) {
            e.printStackTrace();
            // Show error if desired
        }
    }


    private void closeDialog() {
        Stage stage = (Stage) Nomprofmofid.getScene().getWindow();
        stage.close();
    }
}
