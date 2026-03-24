package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.service.ArtisteService;
import fr.istic.taa.jaxrs.dto.ArtisteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class ArtisteResourceTest {

    private ArtisteResource resource;
    private ArtisteService service;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        resource = new ArtisteResource();
        service = new ArtisteService();
        adresse = new Adresse(456, "Rue de l'Art", "Lyon");
    }

    @Test
    public void testGetAll() {
        Response response = resource.getAll();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetById() {
        Artiste artiste = service.creerArtiste(
                "TestArtist",
                "Test",
                "test.artist@test.com",
                "password",
                adresse,
                "TestName",
                "TestStyle"
        );

        Response response = resource.getById(artiste.getId());
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
        ArtisteDTO dto = new ArtisteDTO();
        dto.nom = "NewArtist";
        dto.prenom = "New";
        dto.email = "new@art.com";
        dto.password = "password";
        dto.nomDeScene = "NewScene";
        dto.styleArtistique = "Rock";

        Response response = resource.create(dto);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdate() {
        Artiste artiste = service.creerArtiste(
                "ToUpdate",
                "Update",
                "update.artist@test.com",
                "password",
                adresse,
                "UpdateName",
                "UpdateStyle"
        );

        ArtisteDTO dto = new ArtisteDTO();
        dto.nomDeScene = "NewName";
        dto.styleArtistique = "Jazz";

        Response response = resource.update(artiste.getId(), dto);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNotFound() {
        ArtisteDTO dto = new ArtisteDTO();
        dto.nomDeScene = "NewName";

        Response response = resource.update(99999L, dto);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDelete() {
        Artiste artiste = service.creerArtiste(
                "ToDelete",
                "Delete",
                "delete.artist@test.com",
                "password",
                adresse,
                "DeleteName",
                "DeleteStyle"
        );

        Response response = resource.delete(artiste.getId());
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
    public void testGetEvenements() {
        Artiste artiste = service.creerArtiste(
                "ArtistWithEvents",
                "Events",
                "events@art.com",
                "password",
                adresse,
                "EventName",
                "Rock"
        );

        Response response = resource.getEvenements(artiste.getId());
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetEvenementsNotFound() {
        Response response = resource.getEvenements(99999L);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
