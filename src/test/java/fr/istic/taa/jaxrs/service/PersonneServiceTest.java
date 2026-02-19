package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonneServiceTest {

    private PersonneService service;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        service = new PersonneService();
        adresse = new Adresse(111, "Rue de la Personne", "Bordeaux");
    }

    @Test
    public void testGetAll() {
        UtilisateurService userService = new UtilisateurService();
        userService.creerUtilisateur("Personne1", "First", "p1@test.com", "pass1", adresse, "0111111111");
        userService.creerUtilisateur("Personne2", "Second", "p2@test.com", "pass2", adresse, "0222222222");

        List<?> personnes = service.getAll();
        assertNotNull(personnes);
        assertTrue(personnes.size() >= 2);
    }

    @Test
    public void testGetById() {
        UtilisateurService userService = new UtilisateurService();
        Utilisateur created = userService.creerUtilisateur(
                "TestPersonne",
                "Test",
                "test.personne@test.com",
                "password",
                adresse,
                "0999999999"
        );

        assertNotNull(created.getId());
        assertNotNull(service.getById(created.getId()));
    }

    @Test
    public void testUpdate() {
        UtilisateurService userService = new UtilisateurService();
        Utilisateur created = userService.creerUtilisateur(
                "OriginalPersonne",
                "Original",
                "original.personne@test.com",
                "password",
                adresse,
                "0111111111"
        );

        Utilisateur updated = new Utilisateur();
        updated.setEmail("newemail@test.com");

        assertNotNull(service.update(created.getId(), updated));
    }

    @Test
    public void testDelete() {
        UtilisateurService userService = new UtilisateurService();
        Utilisateur created = userService.creerUtilisateur(
                "ToDeletePersonne",
                "Delete",
                "delete.personne@test.com",
                "password",
                adresse,
                "0333333333"
        );

        Long id = created.getId();
        service.delete(id);

        assertNull(service.getById(id));
    }

    @Test
    public void testGetByIdNotFound() {
        assertNull(service.getById(99999L));
    }
}

