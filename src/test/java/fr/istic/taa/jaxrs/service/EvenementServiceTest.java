package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.domain.TypeEvenement;
import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.tools.tools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementServiceTest {

    private EvenementService service;
    private TypeEvenementService typeService;
    private ArtisteService artisteService;
    private OrganisateurService orgService;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        service = new EvenementService();
        typeService = new TypeEvenementService();
        artisteService = new ArtisteService();
        orgService = new OrganisateurService();
        adresse = new Adresse(666, "Rue de l'Événement", "Lille");

        // Créer un type d'événement
        typeService.creerTypeEvenement("Concert", "Un concert");
    }

    @Test
    public void testCreerEvenement() {
        LocalDateTime now = LocalDateTime.now();

        Evenement event = service.creerEvenement(
                "Grand Concert",
                "Stade Municipal",
                now.plusDays(7),
                5000,
                "Un grand concert mondial",
                tools.StatutEvenement.CREE,
                1
        );

        assertNotNull(event);
        assertEquals("Grand Concert", event.getLibelle());
        assertEquals("Stade Municipal", event.getLieu());
        assertEquals(5000, event.getCapacite());
        assertEquals(tools.StatutEvenement.CREE, event.getStatut());
    }

    @Test
    public void testGetAll() {
        LocalDateTime now = LocalDateTime.now();

        service.creerEvenement("Event1", "Lieu1", now.plusDays(1), 100, "Description1",
                tools.StatutEvenement.CREE, 1);
        service.creerEvenement("Event2", "Lieu2", now.plusDays(2), 200, "Description2",
                tools.StatutEvenement.CREE, 1);

        List<Evenement> events = service.getAll();
        assertNotNull(events);
        assertTrue(events.size() >= 2);
    }

    @Test
    public void testGetById() {
        LocalDateTime now = LocalDateTime.now();

        Evenement created = service.creerEvenement(
                "TestEvent",
                "TestLieu",
                now.plusDays(3),
                300,
                "Test Description",
                tools.StatutEvenement.CREE,
                1
        );

        assertNotNull(created.getId());
        Evenement retrieved = service.getById(created.getId());
        assertNotNull(retrieved);
        assertEquals("TestEvent", retrieved.getLibelle());
    }

    @Test
    public void testUpdate() {
        LocalDateTime now = LocalDateTime.now();

        Evenement created = service.creerEvenement(
                "OriginalEvent",
                "OriginalLieu",
                now.plusDays(1),
                100,
                "Original Description",
                tools.StatutEvenement.CREE,
                1
        );

        Evenement updated = new Evenement();
        updated.setLibelle("UpdatedEvent");
        updated.setDescription("Updated Description");

        Evenement result = service.update(created.getId(), updated);
        assertNotNull(result);
        assertEquals("UpdatedEvent", result.getLibelle());
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    public void testDelete() {
        LocalDateTime now = LocalDateTime.now();

        Evenement created = service.creerEvenement(
                "ToDeleteEvent",
                "ToDeleteLieu",
                now.plusDays(1),
                100,
                "To Delete Description",
                tools.StatutEvenement.CREE,
                1
        );

        Long id = created.getId();
        service.delete(id);

        Evenement retrieved = service.getById(id);
        assertNull(retrieved);
    }

    @Test
    public void testGetByIdNotFound() {
        Evenement event = service.getById(99999L);
        assertNull(event);
    }

    @Test
    public void testAjouterOrganisateur() {
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

        Organisateur org = orgService.creerOrganisateur(
                "OrgName",
                "OrgFirst",
                "org@test.com",
                "password",
                adresse,
                "OrgCompany",
                "0123456789"
        );

        Evenement updated = service.ajouterOrganisateur(
                event.getId().intValue(),
                org.getId().intValue()
        );

        assertNotNull(updated);
        assertTrue(updated.getOrganisateurs().size() > 0);
    }

    @Test
    public void testAjouterArtiste() {
        LocalDateTime now = LocalDateTime.now();

        Evenement event = service.creerEvenement(
                "EventWithArtist",
                "Lieu",
                now.plusDays(1),
                100,
                "Description",
                tools.StatutEvenement.CREE,
                1
        );

        Artiste artist = artisteService.creerArtiste(
                "ArtistName",
                "ArtistFirst",
                "artist@test.com",
                "password",
                adresse,
                "ArtisticName",
                "Rock"
        );

        Evenement updated = service.ajouterArtiste(
                event.getId().intValue(),
                artist.getId().intValue()
        );

        assertNotNull(updated);
        assertTrue(updated.getArtistes().size() > 0);
    }
}

