package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.domain.Administrateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdministrateurServiceTest {

    private AdministrateurService service;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        service = new AdministrateurService();
        adresse = new Adresse(123, "Rue de Test", "Paris");
    }

    @Test
    public void testCreerAdministrateur() {
        Administrateur admin = service.creerAdministrateur(
                "Dupont",
                "Jean",
                "jean.dupont@test.com",
                "password123",
                adresse,
                "SUPER_ADMIN"
        );

        assertNotNull(admin);
        assertEquals("Dupont", admin.getNom());
        assertEquals("Jean", admin.getPrenom());
        assertEquals("jean.dupont@test.com", admin.getEmail());
        assertEquals("SUPER_ADMIN", admin.getRole());
    }

    @Test
    public void testGetAll() {
        // Créer quelques administrateurs
        service.creerAdministrateur("Admin1", "First", "admin1@test.com", "pass1", adresse, "ADMIN");
        service.creerAdministrateur("Admin2", "Second", "admin2@test.com", "pass2", adresse, "ADMIN");

        List<Administrateur> admins = service.getAll();
        assertNotNull(admins);
        assertTrue(admins.size() >= 2);
    }

    @Test
    public void testGetById() {
        Administrateur created = service.creerAdministrateur(
                "TestAdmin",
                "Test",
                "test@test.com",
                "password",
                adresse,
                "ADMIN"
        );

        assertNotNull(created.getId());
        Administrateur retrieved = service.getById(created.getId());
        assertNotNull(retrieved);
        assertEquals("TestAdmin", retrieved.getNom());
    }

    @Test
    public void testUpdate() {
        Administrateur created = service.creerAdministrateur(
                "OriginalName",
                "Original",
                "original@test.com",
                "password",
                adresse,
                "ADMIN"
        );

        Administrateur updated = new Administrateur();
        updated.setNom("ModifiedName");
        updated.setRole("SUPER_ADMIN");

        Administrateur result = service.update(created.getId(), updated);
        assertNotNull(result);
        assertEquals("ModifiedName", result.getNom());
        assertEquals("SUPER_ADMIN", result.getRole());
    }

    @Test
    public void testDelete() {
        Administrateur created = service.creerAdministrateur(
                "ToDelete",
                "Delete",
                "delete@test.com",
                "password",
                adresse,
                "ADMIN"
        );

        Long id = created.getId();
        service.delete(id);

        Administrateur retrieved = service.getById(id);
        assertNull(retrieved);
    }

    @Test
    public void testGetByIdNotFound() {
        Administrateur admin = service.getById(99999L);
        assertNull(admin);
    }

    @Test
    public void testGetByRole() {
        service.creerAdministrateur("Admin1", "First", "admin1@test.com", "pass1", adresse, "ADMIN");
        service.creerAdministrateur("Admin2", "Second", "admin2@test.com", "pass2", adresse, "SUPER_ADMIN");

        List<Administrateur> admins = service.getByRole("ADMIN");
        List<Administrateur> supers = service.getByRole("SUPER_ADMIN");

        assertNotNull(admins);
        assertNotNull(supers);
        assertTrue(admins.size() >= 1);
        assertTrue(supers.size() >= 1);
    }

    @Test
    public void testGetByEmail() {
        service.creerAdministrateur("Admin1", "First", "unique@admin.com", "pass1", adresse, "ADMIN");
        service.creerAdministrateur("Admin2", "Second", "another@admin.com", "pass2", adresse, "ADMIN");

        Administrateur byEmail = service.getUniqueByEmail("unique@admin.com");
        assertNotNull(byEmail);
        assertEquals("unique@admin.com", byEmail.getEmail());
    }

    @Test
    public void testGetTotalAdministrateurs() {
        service.creerAdministrateur("Admin1", "First", "admin1@test.com", "pass1", adresse, "ADMIN");
        service.creerAdministrateur("Admin2", "Second", "admin2@test.com", "pass2", adresse, "ADMIN");

        Long total = service.getTotalAdministrateurs();
        assertNotNull(total);
        assertTrue(total >= 2);
    }

    @Test
    public void testGetRepartitionParRole() {
        service.creerAdministrateur("Admin1", "First", "admin1@test.com", "pass1", adresse, "ADMIN");
        service.creerAdministrateur("Admin2", "Second", "admin2@test.com", "pass2", adresse, "SUPER_ADMIN");
        service.creerAdministrateur("Admin3", "Third", "admin3@test.com", "pass3", adresse, "ADMIN");

        java.util.Map<String, Long> repartition = service.getRepartitionParRole();
        assertNotNull(repartition);
        assertTrue(repartition.containsKey("ADMIN"));
        assertTrue(repartition.containsKey("SUPER_ADMIN"));
        assertEquals(2, repartition.get("ADMIN"));
        assertEquals(1, repartition.get("SUPER_ADMIN"));
    }
}
