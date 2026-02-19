package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.TypeEvenement;
import fr.istic.taa.jaxrs.service.TypeEvenementService;
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
        TypeEvenement type = new TypeEvenement();
        type.setLibelle("NewType");
        type.setDescription("New Type Description");

        Response response = resource.create(type);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdate() {
        TypeEvenement type = service.creerTypeEvenement(
                "Spectacle",
                "Un spectacle"
        );

        TypeEvenement updated = new TypeEvenement();
        updated.setLibelle("Spectacle Musical");
        updated.setDescription("Un spectacle musical amélioré");

        Response response = resource.update(type.getId(), updated);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNotFound() {
        TypeEvenement updated = new TypeEvenement();
        updated.setLibelle("UpdatedType");

        Response response = resource.update(99999L, updated);
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

