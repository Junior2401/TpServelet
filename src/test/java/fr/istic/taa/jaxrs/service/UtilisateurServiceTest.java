package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurServiceTest {

    private UtilisateurService service;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        service = new UtilisateurService();
        adresse = new Adresse(789, "Rue du Client", "Marseille");
    }

    @Test
    public void testCreerUtilisateur() {
        Utilisateur user = service.creerUtilisateur(
                "Martin",
                "Sophie",
                "sophie.martin@test.com",
                "userpass123",
                adresse,
                "0123456789"
        );

        assertNotNull(user);
        assertEquals("Martin", user.getNom());
        assertEquals("Sophie", user.getPrenom());
        assertEquals("sophie.martin@test.com", user.getEmail());
        assertEquals("0123456789", user.getTelephone());
    }

    @Test
    public void testGetAll() {
        service.creerUtilisateur("User1", "First", "user1@test.com", "pass1", adresse, "0111111111");
        service.creerUtilisateur("User2", "Second", "user2@test.com", "pass2", adresse, "0222222222");

        List<Utilisateur> users = service.getAll();
        assertNotNull(users);
        assertTrue(users.size() >= 2);
    }

    @Test
    public void testGetById() {
        Utilisateur created = service.creerUtilisateur(
                "TestUser",
                "Test",
                "test.user@test.com",
                "password",
                adresse,
                "0999999999"
        );

        assertNotNull(created.getId());
        Utilisateur retrieved = service.getById(created.getId());
        assertNotNull(retrieved);
        assertEquals("TestUser", retrieved.getNom());
    }

    @Test
    public void testUpdate() {
        Utilisateur created = service.creerUtilisateur(
                "OriginalUser",
                "Original",
                "original.user@test.com",
                "password",
                adresse,
                "0111111111"
        );

        Utilisateur updated = new Utilisateur();
        updated.setTelephone("0777777777");
        updated.setEmail("newemail@test.com");

        Utilisateur result = service.update(created.getId(), updated);
        assertNotNull(result);
        assertEquals("0777777777", result.getTelephone());
        assertEquals("newemail@test.com", result.getEmail());
    }

    @Test
    public void testDelete() {
        Utilisateur created = service.creerUtilisateur(
                "ToDeleteUser",
                "Delete",
                "delete.user@test.com",
                "password",
                adresse,
                "0333333333"
        );

        Long id = created.getId();
        service.delete(id);

        Utilisateur retrieved = service.getById(id);
        assertNull(retrieved);
    }

    @Test
    public void testGetByIdNotFound() {
        Utilisateur user = service.getById(99999L);
        assertNull(user);
    }
}

