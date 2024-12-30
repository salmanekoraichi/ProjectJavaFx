package server.projectfinal.Services;

/**
 * This code is written by Salmane Koraichi
 **/

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for aggregating dashboard data.
 * Provides methods to retrieve statistics for the dashboard.
 */
public class DashboardService {

    private final EtudiantService etudiantService;
    private final ProfesseurService professeurService;
    private final InscriptionsService inscriptionsService;

    /**
     * Constructs a DashboardService with the required dependencies.
     *
     * @param etudiantService     the service managing Etudiant entities
     * @param professeurService   the service managing Professeur entities
     * @param inscriptionsService the service managing Inscription entities
     */
    public DashboardService(EtudiantService etudiantService,
                            ProfesseurService professeurService,
                            InscriptionsService inscriptionsService) {
        this.etudiantService = etudiantService;
        this.professeurService = professeurService;
        this.inscriptionsService = inscriptionsService;
    }

    /**
     * Retrieves the total number of students.
     *
     * @return total students count
     */
    public int getTotalEtudiants() {
        return etudiantService.getAllEtudiants().size();
    }

    /**
     * Retrieves the total number of professors.
     *
     * @return total professors count
     */
    public int getTotalProfesseurs() {
        return professeurService.getAllProfesseurs().size();
    }

    /**
     * Retrieves the total number of modules.
     *
     * @return total modules count
     */
    public int getTotalModules() {
        return professeurService.getAllProfesseurs().stream()
                .mapToInt(professeur -> professeurService.getModulesByProfesseur(professeur.getId()).size())
                .sum();
    }

    /**
     * Retrieves the total number of inscriptions.
     *
     * @return total inscriptions count
     */
    public int getTotalInscriptions() {
        return inscriptionsService.getAllInscriptions().size();
    }

    /**
     * Retrieves the number of students grouped by their promotions.
     *
     * @return map of promotion to student count
     */
    public Map<String, Integer> getEtudiantsByPromotion() {
        Map<String, Integer> promotionCounts = new HashMap<>();
        etudiantService.getAllEtudiants().forEach(etudiant -> {
            String promotion = etudiant.getPromotion();
            promotionCounts.put(promotion, promotionCounts.getOrDefault(promotion, 0) + 1);
        });
        return promotionCounts;
    }

    /**
     * Retrieves the number of inscriptions per year.
     *
     * @return map of year to inscription count
     */
    public Map<String, Integer> getInscriptionsByYear() {
        Map<String, Integer> inscriptionsByYear = new HashMap<>();
        inscriptionsService.getAllInscriptions().forEach(inscription -> {
            String dateInscription = inscription.getDateInscription();
            if (dateInscription != null && dateInscription.length() >= 4) {
                String year = dateInscription.substring(0, 4); // Assuming date format is YYYY-MM-DD
                inscriptionsByYear.put(year, inscriptionsByYear.getOrDefault(year, 0) + 1);
            }
        });
        return inscriptionsByYear;
    }

    /**
     * Retrieves the number of modules taught by each professor.
     *
     * @return map of professor name to module count
     */
    public Map<String, Integer> getModulesByProfesseur() {
        Map<String, Integer> modulesByProf = new HashMap<>();
        professeurService.getAllProfesseurs().forEach(professeur -> {
            String profName = professeur.getNom() + " " + professeur.getPrenom();
            int moduleCount = professeurService.getModulesByProfesseur(professeur.getId()).size();
            modulesByProf.put(profName, moduleCount);
        });
        return modulesByProf;
    }
}

