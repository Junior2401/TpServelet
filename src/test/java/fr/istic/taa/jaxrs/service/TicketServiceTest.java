package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.domain.Utilisateur;
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
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        service = new TicketService();
        evenementService = new EvenementService();
        utilisateurService = new UtilisateurService();
        adresse = new Adresse(555, "Rue du Ticket", "Nantes");

        // Créer un type d'événement
        // typeService.creerTypeEvenement("Concert", "Un concert musical");
    }

    @Test
    public void testCreerTicket() {
        // Créer un événement d'abord
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;
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
                event.getId(),
                user.getId()
        );

        assertNotNull(ticket);
        assertEquals("A1", ticket.getNumeroPlace());
        assertEquals("Stalle", ticket.getPlace());
        assertEquals(50, ticket.getPrix());
    }

    @Test
    public void testGetAll() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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
                event.getId(), user.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId(), user.getId());

        List<Ticket> tickets = service.getAll();
        assertNotNull(tickets);
        assertTrue(tickets.size() >= 2);
    }

    @Test
    public void testGetById() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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
                event.getId(),
                user.getId()
        );

        assertNotNull(created.getId());
        Ticket retrieved = service.getById(created.getId());
        assertNotNull(retrieved);
        assertEquals("B1", retrieved.getNumeroPlace());
    }

    @Test
    public void testDelete() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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
                event.getId(),
                user.getId()
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

    @Test
    public void testGetByUtilisateur() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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
                event.getId(), user.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId(), user.getId());

        List<Ticket> tickets = service.getByUtilisateur(user.getId());
        assertNotNull(tickets);
        assertTrue(tickets.size() >= 2);
        assertEquals(user.getId(), tickets.get(0).getUtilisateur().getId());
    }

    @Test
    public void testGetByEvenement() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

        Evenement event = evenementService.creerEvenement(
                "Concert",
                "Salle",
                now.plusDays(1),
                100,
                "Description",
                tools.StatutEvenement.CREE,
                typeId
        );

        Utilisateur user1 = utilisateurService.creerUtilisateur(
                "User1", "Test", "user1@test.com", "pass",
                adresse, "0111111111"
        );
        Utilisateur user2 = utilisateurService.creerUtilisateur(
                "User2", "Test", "user2@test.com", "pass",
                adresse, "0222222222"
        );

        service.creerTicket("A1", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId(), user1.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId(), user2.getId());

        List<Ticket> tickets = service.getByEvenement(event.getId());
        assertNotNull(tickets);
        assertTrue(tickets.size() >= 2);
        assertEquals(event.getId(), tickets.get(0).getEvenement().getId());
    }

    @Test
    public void testGetByStatut() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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
                event.getId(), user.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ANNULE, 50, now, now.plusMinutes(1), now.plusMinutes(2),
                event.getId(), user.getId());

        List<Ticket> achetes = service.getByStatut(tools.StatutTicket.ACHETE);
        List<Ticket> annules = service.getByStatut(tools.StatutTicket.ANNULE);

        assertNotNull(achetes);
        assertNotNull(annules);
        assertTrue(achetes.size() >= 1);
        assertTrue(annules.size() >= 1);
    }

    @Test
    public void testGetPurchasedAfter() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minusDays(1);
        long typeId = 1L;

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

        service.creerTicket("A1", "Stalle", tools.StatutTicket.ACHETE, 50, past, null, null,
                event.getId(), user.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId(), user.getId());

        List<Ticket> recent = service.getPurchasedAfter(past.plusMinutes(1));
        assertNotNull(recent);
        assertTrue(recent.size() >= 1);
    }

    @Test
    public void testGetCancelledNotRefunded() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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

        service.creerTicket("A1", "Stalle", tools.StatutTicket.ANNULE, 50, now.minusDays(1), now, null,
                event.getId(), user.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ANNULE, 50, now.minusDays(1), now, now.plusMinutes(1),
                event.getId(), user.getId());

        List<Ticket> notRefunded = service.getCancelledNotRefunded();
        assertNotNull(notRefunded);
        assertTrue(notRefunded.size() >= 1);
        assertNull(notRefunded.get(0).getDateRemboursement());
    }

    @Test
    public void testGetAbovePrice() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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

        service.creerTicket("A1", "Stalle", tools.StatutTicket.ACHETE, 30, now, null, null,
                event.getId(), user.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ACHETE, 60, now, null, null,
                event.getId(), user.getId());

        List<Ticket> expensive = service.getAbovePrice(40);
        assertNotNull(expensive);
        assertTrue(expensive.size() >= 1);
        assertTrue(expensive.get(0).getPrix() > 40);
    }

    @Test
    public void testGetChiffreAffaireEvenement() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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
                event.getId(), user.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ANNULE, 50, now, now.plusMinutes(1), null,
                event.getId(), user.getId());

        Long ca = service.getChiffreAffaireEvenement(event.getId());
        assertNotNull(ca);
        assertEquals(50, ca); // Only non-cancelled
    }

    @Test
    public void testGetTauxRemplissage() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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
                event.getId(), user.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId(), user.getId());

        Double taux = service.getTauxRemplissage(event.getId());
        assertNotNull(taux);
        assertEquals(2.0, taux); // 2/100 * 100 = 2%
    }

    @Test
    public void testGetRepartitionParStatut() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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
                event.getId(), user.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ANNULE, 50, now, now.plusMinutes(1), null,
                event.getId(), user.getId());

        java.util.Map<tools.StatutTicket, Long> repartition = service.getRepartitionParStatut();
        assertNotNull(repartition);
        assertTrue(repartition.containsKey(tools.StatutTicket.ACHETE));
        assertTrue(repartition.containsKey(tools.StatutTicket.ANNULE));
    }

    @Test
    public void testGetManqueAGagnerAnnulations() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

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

        service.creerTicket("A1", "Stalle", tools.StatutTicket.ANNULE, 50, now, now.plusMinutes(1), null,
                event.getId(), user.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ANNULE, 30, now, now.plusMinutes(1), null,
                event.getId(), user.getId());

        Long manque = service.getManqueAGagnerAnnulations();
        assertNotNull(manque);
        assertEquals(80, manque); // 50 + 30
    }

    @Test
    public void testGetTopAcheteurs() {
        LocalDateTime now = LocalDateTime.now();
        long typeId = 1L;

        Evenement event = evenementService.creerEvenement(
                "Concert",
                "Salle",
                now.plusDays(1),
                100,
                "Description",
                tools.StatutEvenement.CREE,
                typeId
        );

        Utilisateur user1 = utilisateurService.creerUtilisateur(
                "User1", "Test", "user1@test.com", "pass",
                adresse, "0111111111"
        );
        Utilisateur user2 = utilisateurService.creerUtilisateur(
                "User2", "Test", "user2@test.com", "pass",
                adresse, "0222222222"
        );

        service.creerTicket("A1", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId(), user1.getId());
        service.creerTicket("A2", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId(), user1.getId());
        service.creerTicket("A3", "Stalle", tools.StatutTicket.ACHETE, 50, now, null, null,
                event.getId(), user2.getId());

        java.util.Map<String, Long> top = service.getTopAcheteurs(5);
        assertNotNull(top);
        assertTrue(top.size() >= 2);
        // User1 should have 2, User2 1
    }
}
