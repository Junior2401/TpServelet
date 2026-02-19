package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.service.TicketService;
import fr.istic.taa.jaxrs.service.EvenementService;
import fr.istic.taa.jaxrs.service.UtilisateurService;
import fr.istic.taa.jaxrs.service.TypeEvenementService;
import fr.istic.taa.jaxrs.tools.tools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TicketResourceTest {

    private TicketResource resource;
    private TicketService service;
    private EvenementService evenementService;
    private UtilisateurService utilisateurService;
    private TypeEvenementService typeService;
    private Adresse adresse;
    private Evenement event;
    private Utilisateur user;

    @BeforeEach
    public void setUp() {
        resource = new TicketResource();
        service = new TicketService();
        evenementService = new EvenementService();
        utilisateurService = new UtilisateurService();
        typeService = new TypeEvenementService();
        adresse = new Adresse(555, "Rue du Ticket", "Nantes");

        // Créer un type d'événement
        typeService.creerTypeEvenement("Concert", "Un concert");

        // Créer un événement
        LocalDateTime now = LocalDateTime.now();
        event = evenementService.creerEvenement(
                "Concert Test",
                "Salle de Concert",
                now.plusDays(1),
                100,
                "Un concert de test",
                tools.StatutEvenement.CREE,
                1
        );

        // Créer un utilisateur
        user = utilisateurService.creerUtilisateur(
                "BuyerName",
                "FirstName",
                "buyer@test.com",
                "password",
                adresse,
                "0123456789"
        );
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
        Ticket ticket = service.creerTicket(
                "A1",
                "Stalle",
                tools.StatutTicket.ACHETE,
                50,
                now,
                null,
                null,
                event.getId().intValue(),
                user.getId().intValue()
        );

        Response response = resource.getById(ticket.getId());
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
        Ticket ticket = new Ticket();
        ticket.setNumeroPlace("B1");
        ticket.setPlace("Balcon");
        ticket.setStatut(tools.StatutTicket.ACHETE);
        ticket.setPrix(40);
        ticket.setDateAchat(LocalDateTime.now());
        ticket.setEvenement(event);
        ticket.setUtilisateur(user);

        Response response = resource.create(ticket);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdate() {
        LocalDateTime now = LocalDateTime.now();
        Ticket ticket = service.creerTicket(
                "C1",
                "Parterre",
                tools.StatutTicket.ACHETE,
                60,
                now,
                null,
                null,
                event.getId().intValue(),
                user.getId().intValue()
        );

        Ticket updated = new Ticket();
        updated.setStatut(tools.StatutTicket.ANNULE);
        updated.setDateAnnulation(now.plusDays(1));

        Response response = resource.update(ticket.getId(), updated);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNotFound() {
        Ticket updated = new Ticket();
        updated.setStatut(tools.StatutTicket.ANNULE);

        Response response = resource.update(99999L, updated);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDelete() {
        LocalDateTime now = LocalDateTime.now();
        Ticket ticket = service.creerTicket(
                "D1",
                "VIP",
                tools.StatutTicket.ACHETE,
                100,
                now,
                null,
                null,
                event.getId().intValue(),
                user.getId().intValue()
        );

        Response response = resource.delete(ticket.getId());
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

