package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.service.EvenementService;
import fr.istic.taa.jaxrs.service.ArtisteService;
import fr.istic.taa.jaxrs.service.OrganisateurService;
import fr.istic.taa.jaxrs.service.TypeEvenementService;
import fr.istic.taa.jaxrs.tools.tools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementResourceTest {

    private EvenementResource resource;
    private EvenementService service;
    private TypeEvenementService typeService;
    private ArtisteService artisteService;
    private OrganisateurService orgService;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        resource = new EvenementResource();
        service = new EvenementService();
        typeService = new TypeEvenementService();
        artisteService = new ArtisteService();
        orgService = new OrganisateurService();
        adresse = new Adresse(666, "Rue de l'Événement", "Lille");

        // Créer un type d'événement
        typeService.creerTypeEvenement("Concert", "Un concert");
    }

    @Test
    public void testGetAll() {
        Response response = resource.getAll();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetById() {
        LocalDateTime now = LocalDateTime.now();
        Evenement event = service.creerEvenement(
                "TestEvent",
                "TestLieu",
                now.plusDays(3),
                300,
                "Test Description",
                tools.StatutEvenement.CREE,
                1
        );

        Response response = resource.getById(event.getId());
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
        LocalDateTime now = LocalDateTime.now();
        Evenement event = new Evenement();
        event.setLibelle("NewEvent");
        event.setLieu("NewLieu");
        event.setDate(now.plusDays(7));
        event.setCapacite(500);
        event.setDescription("New Description");
        event.setStatut(tools.StatutEvenement.CREE);
        event.setTypeEvenement(typeService.creerTypeEvenement("NewType", "Description"));

        Response response = resource.create(event);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdate() {
        LocalDateTime now = LocalDateTime.now();
        Evenement event = service.creerEvenement(
                "ToUpdate",
                "ToUpdateLieu",
                now.plusDays(1),
                100,
                "To Update Description",
                tools.StatutEvenement.CREE,
                1
        );

        Evenement updated = new Evenement();
        updated.setLibelle("UpdatedEvent");
        updated.setDescription("Updated Description");

        Response response = resource.update(event.getId(), updated);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNotFound() {
        Evenement updated = new Evenement();
        updated.setLibelle("UpdatedEvent");

        Response response = resource.update(99999L, updated);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDelete() {
        LocalDateTime now = LocalDateTime.now();
        Evenement event = service.creerEvenement(
                "ToDelete",
                "ToDeleteLieu",
                now.plusDays(1),
                100,
                "To Delete Description",
                tools.StatutEvenement.CREE,
                1
        );

        Response response = resource.delete(event.getId());
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
    public void testGetOrganisateurs() {
        LocalDateTime now = LocalDateTime.now();
        Evenement event = service.creerEvenement(
                "EventWithOrg",
                "Lieu",
                now.plusDays(1),
                100,
                "Description",
                tools.StatutEvenement.CREE,
                1
        );

        Response response = resource.getOrganisateurs(event.getId());
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetOrganisateursNotFound() {
        Response response = resource.getOrganisateurs(99999L);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetArtistes() {
        LocalDateTime now = LocalDateTime.now();
        Evenement event = service.creerEvenement(
                "EventWithArtists",
                "Lieu",
                now.plusDays(1),
                100,
                "Description",
                tools.StatutEvenement.CREE,
                1
        );

        Response response = resource.getArtistes(event.getId());
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetArtistesNotFound() {
        Response response = resource.getArtistes(99999L);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetTickets() {
        LocalDateTime now = LocalDateTime.now();
        Evenement event = service.creerEvenement(
                "EventWithTickets",
                "Lieu",
                now.plusDays(1),
                100,
                "Description",
                tools.StatutEvenement.CREE,
                1
        );

        Response response = resource.getTickets(event.getId());
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

