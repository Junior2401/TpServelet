package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.TypeEvenement;
import fr.istic.taa.jaxrs.service.TypeEvenementService;
import fr.istic.taa.jaxrs.dto.TypeEvenementDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class TypeEvenementResourceTest {

    private TypeEvenenementResource resource;
    private TypeEvenementService service;

    @BeforeEach
    public void setUp() {
        resource = new TypeEvenenementResource();
        service = new TypeEvenementService();
    }

    @Test
    public void testGetAll() {
        Response response = resource.getAll();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetById() {
        TypeEvenement type = service.creerTypeEvenement(
                "Festival",
                "Un festival"
        );

        Response response = resource.getById(type.getId());
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
        TypeEvenementDTO dto = new TypeEvenementDTO();
        dto.libelle = "NewType";
        dto.description = "New Type Description";

        Response response = resource.create(dto);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdate() {
        TypeEvenement type = service.creerTypeEvenement(
                "Spectacle",
                "Un spectacle"
        );

        TypeEvenementDTO dto = new TypeEvenementDTO();
        dto.libelle = "Spectacle Musical";
        dto.description = "Un spectacle musical amélioré";

        Response response = resource.update(type.getId(), dto);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNotFound() {
        TypeEvenementDTO dto = new TypeEvenementDTO();
        dto.libelle = "UpdatedType";

        Response response = resource.update(99999L, dto);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDelete() {
        TypeEvenement type = service.creerTypeEvenement(
                "Conférence",
                "Une conférence"
        );

        Response response = resource.delete(type.getId());
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
