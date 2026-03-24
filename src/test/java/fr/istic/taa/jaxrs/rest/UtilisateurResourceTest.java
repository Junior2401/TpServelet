package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.service.UtilisateurService;
import fr.istic.taa.jaxrs.dto.UtilisateurDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurResourceTest {

    private UtilisateurResource resource;
    private UtilisateurService service;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        resource = new UtilisateurResource();
        service = new UtilisateurService();
        adresse = new Adresse(789, "Rue du Client", "Marseille");
    }

    @Test
    public void testGetAll() {
        Response response = resource.getAll();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetById() {
        Utilisateur user = service.creerUtilisateur(
                "TestUser",
                "Test",
                "test@test.com",
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
    public void testCreate() {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.nom = "NewUser";
        dto.prenom = "New";
        dto.email = "new@test.com";
        dto.password = "password";
        dto.telephone = "0123456789";

        Response response = resource.create(dto);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdate() {
        Utilisateur user = service.creerUtilisateur(
                "ToUpdate",
                "Update",
                "update@test.com",
                "password",
                adresse,
                "0111111111"
        );

        UtilisateurDTO dto = new UtilisateurDTO();
        dto.telephone = "0777777777";
        dto.email = "newemail@test.com";

        Response response = resource.update(user.getId(), dto);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNotFound() {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.telephone = "0777777777";

        Response response = resource.update(99999L, dto);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDelete() {
        Utilisateur user = service.creerUtilisateur(
                "ToDelete",
                "Delete",
                "delete@test.com",
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

    @Test
    public void testGetTickets() {
        Utilisateur user = service.creerUtilisateur(
                "UserWithTickets",
                "Tickets",
                "tickets@test.com",
                "password",
                adresse,
                "0111111111"
        );

        Response response = resource.getTickets(user.getId());
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetTicketsNotFound() {
        Response response = resource.getTickets(99999L);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
