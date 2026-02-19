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
}

