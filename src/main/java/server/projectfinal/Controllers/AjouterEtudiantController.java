package server.projectfinal.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.projectfinal.DAO.EtudiantDAO;
import server.projectfinal.DAO.EtudiantDAOImpl;
import server.projectfinal.DAO.InscriptionDAO;
import server.projectfinal.DAO.InscriptionDAOImpl;
import server.projectfinal.Models.Etudiant;
import server.projectfinal.Services.EtudiantService;

import java.time.format.DateTimeFormatter;

/**
 * Controller for the "Ajouter Étudiant" dialog.
 */
public class AjouterEtudiantController {



    @FXML
    private DatePicker datedenaissanceadd;

    @FXML
    private TextField emailadd;

    @FXML
    private Button handleaddstudent;

    @FXML
    private TextField matriculeadd;

    @FXML
    private TextField nomadd;

    @FXML
    private TextField prenomadd;

    @FXML
    private TextField promotionadd;

    // Service to handle DB operations
    private final EtudiantService etudiantService;

    public AjouterEtudiantController() {
        // for FXML
        EtudiantDAO etudiantDAO = new EtudiantDAOImpl();
        InscriptionDAO inscriptionDAO = new InscriptionDAOImpl();
        this.etudiantService = new EtudiantService(etudiantDAO, inscriptionDAO);
        // or set it later with a setter
    }

    // Optional: If you load the FXML with a no-arg constructor, you can remove this constructor.
    public AjouterEtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
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
            Etudiant nouvelEtudiant = new Etudiant();
            nouvelEtudiant.setMatricule(matriculeadd.getText());
            nouvelEtudiant.setNom(nomadd.getText());
            nouvelEtudiant.setPrenom(promotionadd.getText());
            nouvelEtudiant.setPromotion(promotionadd.getText());

            // Convert DatePicker to LocalDate if your model expects it
            if (datedenaissanceadd.getValue() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                nouvelEtudiant.setDateNaissance(datedenaissanceadd.getValue().format(formatter));
            }

            nouvelEtudiant.setEmail(emailadd.getText());

            // Save the new étudiant via service
            etudiantService.addEtudiant(nouvelEtudiant);

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
        Stage stage = (Stage) handleaddstudent.getScene().getWindow();
        stage.close();
    }
}
