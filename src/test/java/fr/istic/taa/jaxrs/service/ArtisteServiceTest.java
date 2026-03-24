package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.domain.Adresse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArtisteServiceTest {

    private ArtisteService service;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        service = new ArtisteService();
        adresse = new Adresse(456, "Rue de l'Art", "Lyon");
    }

    @Test
    public void testCreerArtiste() {
        Artiste artiste = service.creerArtiste(
                "Picasso",
                "Pablo",
                "pablo@art.com",
                "artpassword",
                adresse,
                "Picasso",
                "Peinture"
        );

        assertNotNull(artiste);
        assertEquals("Picasso", artiste.getNom());
        assertEquals("Pablo", artiste.getPrenom());
        assertEquals("pablo@art.com", artiste.getEmail());
        assertEquals("Peinture", artiste.getStyleArtistique());
    }

    @Test
    public void testGetAll() {
        service.creerArtiste("Artiste1", "First", "artiste1@test.com", "pass1", adresse, "Name1", "Style1");
        service.creerArtiste("Artiste2", "Second", "artiste2@test.com", "pass2", adresse, "Name2", "Style2");

        List<Artiste> artistes = service.getAll();
        assertNotNull(artistes);
        assertTrue(artistes.size() >= 2);
    }

    @Test
    public void testGetById() {
        Artiste created = service.creerArtiste(
                "TestArtist",
                "Test",
                "test.artist@test.com",
                "password",
                adresse,
                "TestName",
                "TestStyle"
        );

        assertNotNull(created.getId());
        Artiste retrieved = service.getById(created.getId());
        assertNotNull(retrieved);
        assertEquals("TestArtist", retrieved.getNom());
    }

    @Test
    public void testUpdate() {
        Artiste created = service.creerArtiste(
                "OriginalArtist",
                "Original",
                "original.artist@test.com",
                "password",
                adresse,
                "OriginalName",
                "OriginalStyle"
        );

        Artiste updated = new Artiste();
        updated.setNomDeScene("NewName");
        updated.setStyleArtistique("NewStyle");

        Artiste result = service.update(created.getId(), updated);
        assertNotNull(result);
        assertEquals("NewName", result.getNomDeScene());
        assertEquals("NewStyle", result.getStyleArtistique());
    }

    @Test
    public void testDelete() {
        Artiste created = service.creerArtiste(
                "ToDeleteArtist",
                "Delete",
                "delete.artist@test.com",
                "password",
                adresse,
                "DeleteName",
                "DeleteStyle"
        );

        Long id = created.getId();
        service.delete(id);

        Artiste retrieved = service.getById(id);
        assertNull(retrieved);
    }

    @Test
    public void testGetByIdNotFound() {
        Artiste artiste = service.getById(99999L);
        assertNull(artiste);
    }

    @Test
    public void testGetByNomDeScene() {
        service.creerArtiste("Art1", "First", "art1@test.com", "pass1", adresse, "Scene1", "Rock");
        service.creerArtiste("Art2", "Second", "art2@test.com", "pass2", adresse, "Scene2", "Jazz");

        List<Artiste> byScene1 = service.getByNomDeScene("Scene1");
        List<Artiste> byScene2 = service.getByNomDeScene("Scene2");

        assertNotNull(byScene1);
        assertNotNull(byScene2);
        assertTrue(byScene1.size() >= 1);
        assertTrue(byScene2.size() >= 1);
    }

    @Test
    public void testGetByStyleArtistique() {
        service.creerArtiste("Art1", "First", "art1@test.com", "pass1", adresse, "Name1", "Rock");
        service.creerArtiste("Art2", "Second", "art2@test.com", "pass2", adresse, "Name2", "Jazz");
        service.creerArtiste("Art3", "Third", "art3@test.com", "pass3", adresse, "Name3", "Rock");

        List<Artiste> rocks = service.getByStyleArtistique("Rock");
        List<Artiste> jazzs = service.getByStyleArtistique("Jazz");

        assertNotNull(rocks);
        assertNotNull(jazzs);
        assertTrue(rocks.size() >= 2);
        assertTrue(jazzs.size() >= 1);
    }

    @Test
    public void testGetTotalArtistes() {
        service.creerArtiste("Art1", "First", "art1@test.com", "pass1", adresse, "Name1", "Style1");
        service.creerArtiste("Art2", "Second", "art2@test.com", "pass2", adresse, "Name2", "Style2");

        Long total = service.getTotalArtistes();
        assertNotNull(total);
        assertTrue(total >= 2);
    }

    @Test
    public void testGetRepartitionParStyle() {
        service.creerArtiste("Art1", "First", "art1@test.com", "pass1", adresse, "Name1", "Rock");
        service.creerArtiste("Art2", "Second", "art2@test.com", "pass2", adresse, "Name2", "Jazz");
        service.creerArtiste("Art3", "Third", "art3@test.com", "pass3", adresse, "Name3", "Rock");

        java.util.Map<String, Long> repartition = service.getRepartitionParStyle();
        assertNotNull(repartition);
        assertTrue(repartition.containsKey("Rock"));
        assertTrue(repartition.containsKey("Jazz"));
        assertEquals(2, repartition.get("Rock"));
        assertEquals(1, repartition.get("Jazz"));
    }
}
