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
import fr.istic.taa.jaxrs.dto.TicketDTO;
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
                1L
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
        Ticket ticket = new Ticket();
        ticket.setNumeroPlace("A1");
        ticket.setPlace("Stalle");
        ticket.setStatut(tools.StatutTicket.ACHETE);
        ticket.setPrix(50);
        ticket.setDateAchat(LocalDateTime.now());

        ticket = service.create(ticket, event.getId(), user.getId());

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
        TicketDTO dto = new TicketDTO();
        dto.numeroPlace = "B1";
        dto.place = "Balcon";
        dto.statut = "ACHETE";
        dto.prix = 40;
        dto.evenementId = event.getId();
        dto.utilisateurId = user.getId();

        Response response = resource.create(dto);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdate() {
        LocalDateTime now = LocalDateTime.now();
        Ticket ticket = new Ticket();
        ticket.setNumeroPlace("C1");
        ticket.setPlace("Parterre");
        ticket.setStatut(tools.StatutTicket.ACHETE);
        ticket.setPrix(60);
        ticket.setDateAchat(now);
        ticket = service.create(ticket, event.getId(), user.getId());

        TicketDTO dto = new TicketDTO();
        dto.statut = "ANNULE";
        dto.dateAnnulation = now.plusDays(1);

        Response response = resource.update(ticket.getId(), dto);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNotFound() {
        TicketDTO dto = new TicketDTO();
        dto.statut = "ANNULE";

        Response response = resource.update(99999L, dto);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDelete() {
        LocalDateTime now = LocalDateTime.now();
        Ticket ticket = new Ticket();
        ticket.setNumeroPlace("D1");
        ticket.setPlace("VIP");
        ticket.setStatut(tools.StatutTicket.ACHETE);
        ticket.setPrix(100);
        ticket.setDateAchat(now);
        ticket = service.create(ticket, event.getId(), user.getId());

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
