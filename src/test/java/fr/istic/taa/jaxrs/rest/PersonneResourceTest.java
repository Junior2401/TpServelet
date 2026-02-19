package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.service.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class PersonneResourceTest {

    private PersonneResource resource;
    private UtilisateurService utilisateurService;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        resource = new PersonneResource();
        utilisateurService = new UtilisateurService();
        adresse = new Adresse(111, "Rue de la Personne", "Bordeaux");
    }

    @Test
    public void testGetAll() {
        Response response = resource.getAll();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetById() {
        Utilisateur user = utilisateurService.creerUtilisateur(
                "TestPersonne",
                "Test",
                "test.personne@test.com",
                "password",
                adresse,
                "0999999999"
        );

        Response response = resource.getById(user.getId());
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetByIdNotFound() {
        Response response = resource.getById(99999L);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdate() {
        Utilisateur user = utilisateurService.creerUtilisateur(
                "ToUpdate",
                "Update",
                "update.personne@test.com",
                "password",
                adresse,
                "0111111111"
        );

        Utilisateur updated = new Utilisateur();
        updated.setEmail("newemail@test.com");

        Response response = resource.update(user.getId(), updated);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNotFound() {
        Utilisateur updated = new Utilisateur();
        updated.setEmail("newemail@test.com");

        Response response = resource.update(99999L, updated);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDelete() {
        Utilisateur user = utilisateurService.creerUtilisateur(
                "ToDeletePersonne",
                "Delete",
                "delete.personne@test.com",
                "password",
                adresse,
                "0333333333"
        );

        Response response = resource.delete(user.getId());
        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteNotFound() {
        Response response = resource.delete(99999L);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}

