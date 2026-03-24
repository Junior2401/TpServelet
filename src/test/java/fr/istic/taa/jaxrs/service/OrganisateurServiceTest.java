package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrganisateurServiceTest {

    private OrganisateurService service;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        service = new OrganisateurService();
        adresse = new Adresse(321, "Rue de l'Organisation", "Toulouse");
    }

    @Test
    public void testCreerOrganisateur() {
        Organisateur org = service.creerOrganisateur(
                "Dupuis",
                "Marc",
                "marc.dupuis@events.com",
                "orgpass123",
                adresse,
                "EventCorp",
                "0555555555"
        );

        assertNotNull(org);
        assertEquals("Dupuis", org.getNom());
        assertEquals("Marc", org.getPrenom());
        assertEquals("marc.dupuis@events.com", org.getEmail());
        assertEquals("EventCorp", org.getSociete());
        assertEquals("0555555555", org.getTelephonePro());
    }

    @Test
    public void testGetAll() {
        service.creerOrganisateur("Org1", "First", "org1@test.com", "pass1", adresse, "Company1", "0111111111");
        service.creerOrganisateur("Org2", "Second", "org2@test.com", "pass2", adresse, "Company2", "0222222222");

        List<Organisateur> orgs = service.getAll();
        assertNotNull(orgs);
        assertTrue(orgs.size() >= 2);
    }

    @Test
    public void testGetById() {
        Organisateur created = service.creerOrganisateur(
                "TestOrg",
                "Test",
                "test.org@test.com",
                "password",
                adresse,
                "TestCompany",
                "0999999999"
        );

        assertNotNull(created.getId());
        Organisateur retrieved = service.getById(created.getId());
        assertNotNull(retrieved);
        assertEquals("TestOrg", retrieved.getNom());
    }

    @Test
    public void testUpdate() {
        Organisateur created = service.creerOrganisateur(
                "OriginalOrg",
                "Original",
                "original.org@test.com",
                "password",
                adresse,
                "OriginalCompany",
                "0111111111"
        );

        Organisateur updated = new Organisateur();
        updated.setSociete("NewCompany");
        updated.setTelephonePro("0777777777");

        Organisateur result = service.update(created.getId(), updated);
        assertNotNull(result);
        assertEquals("NewCompany", result.getSociete());
        assertEquals("0777777777", result.getTelephonePro());
    }

    @Test
    public void testDelete() {
        Organisateur created = service.creerOrganisateur(
                "ToDeleteOrg",
                "Delete",
                "delete.org@test.com",
                "password",
                adresse,
                "DeleteCompany",
                "0333333333"
        );

        Long id = created.getId();
        service.delete(id);

        Organisateur retrieved = service.getById(id);
        assertNull(retrieved);
    }

    @Test
    public void testGetByIdNotFound() {
        Organisateur org = service.getById(99999L);
        assertNull(org);
    }

    @Test
    public void testGetBySociete() {
        service.creerOrganisateur("Org1", "First", "org1@test.com", "pass1", adresse, "CompanyA", "0111111111");
        service.creerOrganisateur("Org2", "Second", "org2@test.com", "pass2", adresse, "CompanyB", "0222222222");

        List<Organisateur> byCompA = service.getBySociete("CompanyA");
        List<Organisateur> byCompB = service.getBySociete("CompanyB");

        assertNotNull(byCompA);
        assertNotNull(byCompB);
        assertTrue(byCompA.size() >= 1);
        assertTrue(byCompB.size() >= 1);
    }

    @Test
    public void testGetTotalOrganisateurs() {
        service.creerOrganisateur("Org1", "First", "org1@test.com", "pass1", adresse, "Comp1", "0111111111");
        service.creerOrganisateur("Org2", "Second", "org2@test.com", "pass2", adresse, "Comp2", "0222222222");

        Long total = service.getTotalOrganisateurs();
        assertNotNull(total);
        assertTrue(total >= 2);
    }

    @Test
    public void testGetRepartitionParSociete() {
        service.creerOrganisateur("Org1", "First", "org1@test.com", "pass1", adresse, "CompanyA", "0111111111");
        service.creerOrganisateur("Org2", "Second", "org2@test.com", "pass2", adresse, "CompanyB", "0222222222");
        service.creerOrganisateur("Org3", "Third", "org3@test.com", "pass3", adresse, "CompanyA", "0333333333");

        java.util.Map<String, Long> repartition = service.getRepartitionParSociete();
        assertNotNull(repartition);
        assertTrue(repartition.containsKey("CompanyA"));
        assertTrue(repartition.containsKey("CompanyB"));
        assertEquals(2, repartition.get("CompanyA"));
        assertEquals(1, repartition.get("CompanyB"));
    }
}
