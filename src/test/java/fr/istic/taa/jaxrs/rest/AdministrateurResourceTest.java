package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Administrateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.service.AdministrateurService;
import fr.istic.taa.jaxrs.dto.AdministrateurDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class AdministrateurResourceTest {

    private AdministrateurResource resource;
    private AdministrateurService service;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        resource = new AdministrateurResource();
        service = new AdministrateurService();
        adresse = new Adresse(123, "Rue de Test", "Paris");
    }

    @Test
    public void testGetAll() {
        Response response = resource.getAll();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetById() {
        Administrateur admin = service.creerAdministrateur(
                "TestAdmin",
                "Test",
                "test@test.com",
                "password",
                adresse,
                "ADMIN"
        );

        Response response = resource.getById(admin.getId());
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
        AdministrateurDTO dto = new AdministrateurDTO();
        dto.nom = "NewAdmin";
        dto.prenom = "New";
        dto.email = "new@test.com";
        dto.password = "password";
        dto.role = "ADMIN";

        Response response = resource.create(dto);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdate() {
        Administrateur admin = service.creerAdministrateur(
                "ToUpdate",
                "Update",
                "update@test.com",
                "password",
                adresse,
                "ADMIN"
        );

        AdministrateurDTO dto = new AdministrateurDTO();
        dto.nom = "Updated";
        dto.role = "SUPER_ADMIN";

        Response response = resource.update(admin.getId(), dto);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNotFound() {
        AdministrateurDTO dto = new AdministrateurDTO();
        dto.nom = "Updated";

        Response response = resource.update(99999L, dto);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDelete() {
        Administrateur admin = service.creerAdministrateur(
                "ToDelete",
                "Delete",
                "delete@test.com",
                "password",
                adresse,
                "ADMIN"
        );

        Response response = resource.delete(admin.getId());
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
