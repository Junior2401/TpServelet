package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.domain.TypeEvenement;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.tools.tools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TicketServiceTest {

    private TicketService service;
    private EvenementService evenementService;
    private UtilisateurService utilisateurService;
    private TypeEvenementService typeService;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        service = new TicketService();
        evenementService = new EvenementService();
        utilisateurService = new UtilisateurService();
        typeService = new TypeEvenementService();
        adresse = new Adresse(555, "Rue du Ticket", "Nantes");

        // Créer un type d'événement
        typeService.creerTypeEvenement("Concert", "Un concert musical");
    }

    @Test
    public void testCreerTicket() {
        // Créer un événement d'abord
        LocalDateTime now = LocalDateTime.now();
        int typeId = 1;
        Evenement event = evenementService.creerEvenement(
                "Concert Test",
                "Salle de Concert",
                now.plusDays(1),
                100,
                "Un concert de test",
                tools.StatutEvenement.CREE,
                typeId
        );

        // Créer un utilisateur
        Utilisateur user = utilisateurService.creerUtilisateur(
                "BuyerName",
                "FirstName",
                "buyer@test.com",
                "password",
                adresse,
                "0123456789"
        );

        // Créer un ticket
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

        assertNotNull(ticket);
        assertEquals("A1", ticket.getNumeroPlace());
        assertEquals("Stalle", ticket.getPlace());
        assertEquals(50, ticket.getPrix());
    }

    @Test
    public void testGetAll() {
        LocalDateTime now = LocalDateTime.now();
        int typeId = 1;

        Evenement event = evenementService.creerEvenement(
                "Concert",
                "Salle",
                now.plusDays(1),
                100,
                "Description",
                tools.StatutEvenement.CREE,
                typeId
        );

        Utilisateur user = utilisateurService.creerUtilisateur(
                "User", "Test", "user@test.com", "pass",
                adresse, "0111111111"
        );

        service.creerTicket("A1", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId().intValue(), user.getId().intValue());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId().intValue(), user.getId().intValue());

        List<Ticket> tickets = service.getAll();
        assertNotNull(tickets);
        assertTrue(tickets.size() >= 2);
    }

    @Test
    public void testGetById() {
        LocalDateTime now = LocalDateTime.now();
        int typeId = 1;

        Evenement event = evenementService.creerEvenement(
                "Concert",
                "Salle",
                now.plusDays(1),
                100,
                "Description",
                tools.StatutEvenement.CREE,
                typeId
        );

        Utilisateur user = utilisateurService.creerUtilisateur(
                "User", "Test", "user@test.com", "pass",
                adresse, "0111111111"
        );

        Ticket created = service.creerTicket(
                "B1",
                "Balcon",
                tools.StatutTicket.ACHETE,
                30,
                now,
                null,
                null,
                event.getId().intValue(),
                user.getId().intValue()
        );

        assertNotNull(created.getId());
        Ticket retrieved = service.getById(created.getId());
        assertNotNull(retrieved);
        assertEquals("B1", retrieved.getNumeroPlace());
    }

    @Test
    public void testDelete() {
        LocalDateTime now = LocalDateTime.now();
        int typeId = 1;

        Evenement event = evenementService.creerEvenement(
                "Concert",
                "Salle",
                now.plusDays(1),
                100,
                "Description",
                tools.StatutEvenement.CREE,
                typeId
        );

        Utilisateur user = utilisateurService.creerUtilisateur(
                "User", "Test", "user@test.com", "pass",
                adresse, "0111111111"
        );

        Ticket created = service.creerTicket(
                "C1",
                "Parterre",
                tools.StatutTicket.ACHETE,
                40,
                now,
                null,
                null,
                event.getId().intValue(),
                user.getId().intValue()
        );

        Long id = created.getId();
        service.delete(id);

        Ticket retrieved = service.getById(id);
        assertNull(retrieved);
    }

    @Test
    public void testGetByIdNotFound() {
        Ticket ticket = service.getById(99999L);
        assertNull(ticket);
    }
}

