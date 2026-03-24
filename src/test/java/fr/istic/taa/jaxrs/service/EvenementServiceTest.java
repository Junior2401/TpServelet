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
                1L
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
                tools.StatutEvenement.CREE, 1L);
        service.creerEvenement("Event2", "Lieu2", now.plusDays(2), 200, "Description2",
                tools.StatutEvenement.CREE, 1L);

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
                1L
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
                1L
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
                1L
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
                1L
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
                event.getId(),
                org.getId()
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
                1L
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
                event.getId(),
                artist.getId()
        );

        assertNotNull(updated);
        assertTrue(updated.getArtistes().size() > 0);
    }

    @Test
    public void testGetByStatut() {
        LocalDateTime now = LocalDateTime.now();

        service.creerEvenement("Event1", "Lieu1", now.plusDays(1), 100, "Desc1",
                tools.StatutEvenement.CREE, 1L);
        service.creerEvenement("Event2", "Lieu2", now.plusDays(2), 200, "Desc2",
                tools.StatutEvenement.ANNULE, 1L);

        List<Evenement> crees = service.getByStatut(tools.StatutEvenement.CREE);
        List<Evenement> annules = service.getByStatut(tools.StatutEvenement.ANNULE);

        assertNotNull(crees);
        assertNotNull(annules);
        assertTrue(crees.size() >= 1);
        assertTrue(annules.size() >= 1);
    }

    @Test
    public void testGetByTypeEvenement() {
        LocalDateTime now = LocalDateTime.now();

        TypeEvenement type1 = typeService.creerTypeEvenement("Type1", "Desc1");
        TypeEvenement type2 = typeService.creerTypeEvenement("Type2", "Desc2");

        service.creerEvenement("Event1", "Lieu1", now.plusDays(1), 100, "Desc1",
                tools.StatutEvenement.CREE, type1.getId());
        service.creerEvenement("Event2", "Lieu2", now.plusDays(2), 200, "Desc2",
                tools.StatutEvenement.CREE, type2.getId());

        List<Evenement> byType1 = service.getByTypeEvenement(type1.getId());
        List<Evenement> byType2 = service.getByTypeEvenement(type2.getId());

        assertNotNull(byType1);
        assertNotNull(byType2);
        assertTrue(byType1.size() >= 1);
        assertTrue(byType2.size() >= 1);
    }

    @Test
    public void testGetByOrganisateur() {
        LocalDateTime now = LocalDateTime.now();

        Organisateur org1 = orgService.creerOrganisateur("Org1", "First", "org1@test.com", "pass", adresse, "Comp1", "0111111111");
        Organisateur org2 = orgService.creerOrganisateur("Org2", "First", "org2@test.com", "pass", adresse, "Comp2", "0222222222");

        Evenement event1 = service.creerEvenement("Event1", "Lieu1", now.plusDays(1), 100, "Desc1",
                tools.StatutEvenement.CREE, 1L);
        Evenement event2 = service.creerEvenement("Event2", "Lieu2", now.plusDays(2), 200, "Desc2",
                tools.StatutEvenement.CREE, 1L);

        service.ajouterOrganisateur(event1.getId(), org1.getId());
        service.ajouterOrganisateur(event2.getId(), org2.getId());

        List<Evenement> byOrg1 = service.getByOrganisateur(org1.getId());
        List<Evenement> byOrg2 = service.getByOrganisateur(org2.getId());

        assertNotNull(byOrg1);
        assertNotNull(byOrg2);
        assertTrue(byOrg1.size() >= 1);
        assertTrue(byOrg2.size() >= 1);
    }

    @Test
    public void testGetByArtiste() {
        LocalDateTime now = LocalDateTime.now();

        Artiste art1 = artisteService.creerArtiste("Art1", "First", "art1@test.com", "pass", adresse, "Name1", "Style1");
        Artiste art2 = artisteService.creerArtiste("Art2", "First", "art2@test.com", "pass", adresse, "Name2", "Style2");

        Evenement event1 = service.creerEvenement("Event1", "Lieu1", now.plusDays(1), 100, "Desc1",
                tools.StatutEvenement.CREE, 1L);
        Evenement event2 = service.creerEvenement("Event2", "Lieu2", now.plusDays(2), 200, "Desc2",
                tools.StatutEvenement.CREE, 1L);

        service.ajouterArtiste(event1.getId(), art1.getId());
        service.ajouterArtiste(event2.getId(), art2.getId());

        List<Evenement> byArt1 = service.getByArtiste(art1.getId());
        List<Evenement> byArt2 = service.getByArtiste(art2.getId());

        assertNotNull(byArt1);
        assertNotNull(byArt2);
        assertTrue(byArt1.size() >= 1);
        assertTrue(byArt2.size() >= 1);
    }

    @Test
    public void testGetByDate() {
        LocalDateTime date1 = LocalDateTime.now().plusDays(1);
        LocalDateTime date2 = LocalDateTime.now().plusDays(2);

        service.creerEvenement("Event1", "Lieu1", date1, 100, "Desc1",
                tools.StatutEvenement.CREE, 1L);
        service.creerEvenement("Event2", "Lieu2", date2, 200, "Desc2",
                tools.StatutEvenement.CREE, 1L);

        List<Evenement> byDate1 = service.getByDate(date1.toLocalDate());
        List<Evenement> byDate2 = service.getByDate(date2.toLocalDate());

        assertNotNull(byDate1);
        assertNotNull(byDate2);
        assertTrue(byDate1.size() >= 1);
        assertTrue(byDate2.size() >= 1);
    }

    @Test
    public void testGetByLieu() {
        LocalDateTime now = LocalDateTime.now();

        service.creerEvenement("Event1", "Salle A", now.plusDays(1), 100, "Desc1",
                tools.StatutEvenement.CREE, 1L);
        service.creerEvenement("Event2", "Salle B", now.plusDays(2), 200, "Desc2",
                tools.StatutEvenement.CREE, 1L);

        List<Evenement> byLieuA = service.getByLieu("Salle A");
        List<Evenement> byLieuB = service.getByLieu("Salle B");

        assertNotNull(byLieuA);
        assertNotNull(byLieuB);
        assertTrue(byLieuA.size() >= 1);
        assertTrue(byLieuB.size() >= 1);
    }

    @Test
    public void testGetTotalCapacity() {
        LocalDateTime now = LocalDateTime.now();

        service.creerEvenement("Event1", "Lieu1", now.plusDays(1), 100, "Desc1",
                tools.StatutEvenement.CREE, 1L);
        service.creerEvenement("Event2", "Lieu2", now.plusDays(2), 200, "Desc2",
                tools.StatutEvenement.CREE, 1L);

        Long total = service.getTotalCapacity();
        assertNotNull(total);
        assertTrue(total >= 300);
    }

    @Test
    public void testGetTotalEvents() {
        LocalDateTime now = LocalDateTime.now();

        service.creerEvenement("Event1", "Lieu1", now.plusDays(1), 100, "Desc1",
                tools.StatutEvenement.CREE, 1L);
        service.creerEvenement("Event2", "Lieu2", now.plusDays(2), 200, "Desc2",
                tools.StatutEvenement.CREE, 1L);

        Long total = service.getTotalEvents();
        assertNotNull(total);
        assertTrue(total >= 2);
    }

    @Test
    public void testGetRepartitionParStatut() {
        LocalDateTime now = LocalDateTime.now();

        service.creerEvenement("Event1", "Lieu1", now.plusDays(1), 100, "Desc1",
                tools.StatutEvenement.CREE, 1L);
        service.creerEvenement("Event2", "Lieu2", now.plusDays(2), 200, "Desc2",
                tools.StatutEvenement.ANNULE, 1L);

        java.util.Map<tools.StatutEvenement, Long> repartition = service.getRepartitionParStatut();
        assertNotNull(repartition);
        assertTrue(repartition.containsKey(tools.StatutEvenement.CREE));
        assertTrue(repartition.containsKey(tools.StatutEvenement.ANNULE));
    }

    @Test
    public void testGetRepartitionParType() {
        LocalDateTime now = LocalDateTime.now();

        TypeEvenement type1 = typeService.creerTypeEvenement("Type1", "Desc1");
        TypeEvenement type2 = typeService.creerTypeEvenement("Type2", "Desc2");

        service.creerEvenement("Event1", "Lieu1", now.plusDays(1), 100, "Desc1",
                tools.StatutEvenement.CREE, type1.getId());
        service.creerEvenement("Event2", "Lieu2", now.plusDays(2), 200, "Desc2",
                tools.StatutEvenement.CREE, type2.getId());

        java.util.Map<String, Long> repartition = service.getRepartitionParType();
        assertNotNull(repartition);
        assertTrue(repartition.containsKey("Type1"));
        assertTrue(repartition.containsKey("Type2"));
    }

    @Test
    public void testGetEventsByMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMonth = now.plusMonths(1);

        service.creerEvenement("Event1", "Lieu1", now.plusDays(1), 100, "Desc1",
                tools.StatutEvenement.CREE, 1L);
        service.creerEvenement("Event2", "Lieu2", nextMonth.plusDays(1), 200, "Desc2",
                tools.StatutEvenement.CREE, 1L);

        java.util.Map<String, Long> byMonth = service.getEventsByMonth(2023);
        assertNotNull(byMonth);
        // Depending on current month
    }
}
