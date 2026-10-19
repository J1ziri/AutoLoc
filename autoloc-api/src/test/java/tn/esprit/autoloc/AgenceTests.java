package tn.esprit.autoloc;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;

@SpringBootTest
public class AgenceTests {

    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;

    @Autowired
    private IAgenceRepository fullAgenceRepository;

    @Autowired
    private IAgenceService agenceService;

    private void addAgence(CrudRepository<Agence,Long> repository) {
        int ms = (int) System.currentTimeMillis();

        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("TU96-" + ms);
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setCategorie(CategorieVehicule.SUV);
        v1.setStatut(StatutVehicule.MAINTENANCE);
        v1.setTarifJournalier(new BigDecimal("100.00"));

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("TU95-" + ms);
        v2.setMarque("Toyota");
        v2.setModele("Yaris");
        v2.setCategorie(CategorieVehicule.UTILITAIRE);
        v2.setStatut(StatutVehicule.DISPONIBLE);
        v2.setTarifJournalier(new BigDecimal("80.00"));

        v1.setAgence(agence);
        v2.setAgence(agence);
        agence.getVehicules().add(v1);
        agence.getVehicules().add(v2);

        repository.save(agence);
    }
    private void loadAgence(CrudRepository<Agence, Long> repository, String type) {
        Iterable<Agence> agences = repository.findAll();
        StringBuilder sb = new StringBuilder("\n##### Repository type: " + type + " #####\n");
        for (Agence agence : agences) {
            sb.append("\n")
                    .append(agence.getIdAgence()).append(" | ").append(agence.getNom()).append("\n")
                    .append("Vehicules Count : ").append(agence.getVehicules().size()).append("\n");
            for (Vehicule vehicule : agence.getVehicules()) {
                sb.append("=== ").append(vehicule.getIdVehicule()).append("|")
                        .append(vehicule.getImmatriculation()).append("\n");
            }
        }
        Assertions.fail(sb.toString());
    }

    @Test
    public void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    @Test
    public void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }

    @Test
    public void basicLoadAgence() {
        loadAgence(basicAgenceRepository, "basic");
    }

    @Test
    public void fullLoadAgence() {
        loadAgence(fullAgenceRepository, "full");
    }

    @Test
    public void loadSortedAgences() {
        Iterable<Agence> agences = fullAgenceRepository.findAll(Sort.by("idAgence").descending());
        StringBuilder sb = new StringBuilder("\n##### Sorted Agences (id desc) #####\n");
        for (Agence agence : agences) {
            sb.append("\n")
                    .append(agence.getIdAgence()).append(" | ").append(agence.getNom()).append("\n");
        }
        Assertions.fail(sb.toString());
    }

    @Test
    public void loadPagedAgences() {
        Page<Agence> page = fullAgenceRepository.findAll(PageRequest.of(0, 2, Sort.by("idAgence").descending()));
        StringBuilder sb = new StringBuilder("\n##### Paged Agences (id desc, lot de 2) #####\n");
        sb.append("Nombre total de pages : ").append(page.getTotalPages()).append("\n");
        sb.append("Page en cours : ").append(page.getNumber()).append("\n");
        for (Agence agence : page.getContent()) {
            sb.append("\n")
                    .append(agence.getIdAgence()).append(" | ").append(agence.getNom()).append("\n");
        }
        Assertions.fail(sb.toString());
    }

    @Test
    public void createAgence() {
        int ms = (int) System.currentTimeMillis();
        Agence agence = new Agence();
        agence.setNom("Agence Service " + ms);
        agence.setVille("Ariana");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setTelephone("71234567");

        Agence created = agenceService.create(agence);
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getIdAgence());
        Assertions.assertEquals("Agence Service " + ms, created.getNom());
    }
}

@Repository
interface AgenceRepositoryMock extends CrudRepository<Agence, Long> { }