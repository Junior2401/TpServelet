package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurServiceTest {

    private UtilisateurService service;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        service = new UtilisateurService();
        adresse = new Adresse(789, "Rue du Client", "Marseille");
    }

    @Test
    public void testCreerUtilisateur() {
        Utilisateur user = service.creerUtilisateur(
                "Martin",
                "Sophie",
                "sophie.martin@test.com",
                "userpass123",
                adresse,
                "0123456789"
        );

        assertNotNull(user);
        assertEquals("Martin", user.getNom());
        assertEquals("Sophie", user.getPrenom());
        assertEquals("sophie.martin@test.com", user.getEmail());
        assertEquals("0123456789", user.getTelephone());
    }

    @Test
    public void testGetAll() {
        service.creerUtilisateur("User1", "First", "user1@test.com", "pass1", adresse, "0111111111");
        service.creerUtilisateur("User2", "Second", "user2@test.com", "pass2", adresse, "0222222222");

        List<Utilisateur> users = service.getAll();
        assertNotNull(users);
        assertTrue(users.size() >= 2);
    }

    @Test
    public void testGetById() {
        Utilisateur created = service.creerUtilisateur(
                "TestUser",
                "Test",
                "test.user@test.com",
                "password",
                adresse,
                "0999999999"
        );

        assertNotNull(created.getId());
        Utilisateur retrieved = service.getById(created.getId());
        assertNotNull(retrieved);
        assertEquals("TestUser", retrieved.getNom());
    }

    @Test
    public void testUpdate() {
        Utilisateur created = service.creerUtilisateur(
                "OriginalUser",
                "Original",
                "original.user@test.com",
                "password",
                adresse,
                "0111111111"
        );

        Utilisateur updated = new Utilisateur();
        updated.setTelephone("0777777777");
        updated.setEmail("newemail@test.com");

        Utilisateur result = service.update(created.getId(), updated);
        assertNotNull(result);
        assertEquals("0777777777", result.getTelephone());
        assertEquals("newemail@test.com", result.getEmail());
    }

    @Test
    public void testDelete() {
        Utilisateur created = service.creerUtilisateur(
                "ToDeleteUser",
                "Delete",
                "delete.user@test.com",
                "password",
                adresse,
                "0333333333"
        );

        Long id = created.getId();
        service.delete(id);

        Utilisateur retrieved = service.getById(id);
        assertNull(retrieved);
    }

    @Test
    public void testGetByIdNotFound() {
        Utilisateur user = service.getById(99999L);
        assertNull(user);
    }

    @Test
    public void testGetByNom() {
        service.creerUtilisateur("Dupont", "Jean", "dupont@test.com", "pass1", adresse, "0111111111");
        service.creerUtilisateur("Martin", "Paul", "martin@test.com", "pass2", adresse, "0222222222");

        List<Utilisateur> duponts = service.getByNom("Dupont");
        List<Utilisateur> martins = service.getByNom("Martin");

        assertNotNull(duponts);
        assertNotNull(martins);
        assertTrue(duponts.size() >= 1);
        assertTrue(martins.size() >= 1);
    }

    @Test
    public void testGetByEmail() {
        service.creerUtilisateur("User1", "First", "unique@test.com", "pass1", adresse, "0111111111");
        service.creerUtilisateur("User2", "Second", "another@test.com", "pass2", adresse, "0222222222");

        Utilisateur byEmail = service.getUniqueByEmail("unique@test.com");
        assertNotNull(byEmail);
        assertEquals("unique@test.com", byEmail.getEmail());
    }

    @Test
    public void testGetByTelephone() {
        service.creerUtilisateur("User1", "First", "user1@test.com", "pass1", adresse, "0123456789");
        service.creerUtilisateur("User2", "Second", "user2@test.com", "pass2", adresse, "0987654321");

        List<Utilisateur> byTel = service.getByTelephone("0123456789");
        assertNotNull(byTel);
        assertTrue(byTel.size() >= 1);
        assertEquals("0123456789", byTel.get(0).getTelephone());
    }

    @Test
    public void testGetTotalUtilisateurs() {
        service.creerUtilisateur("User1", "First", "user1@test.com", "pass1", adresse, "0111111111");
        service.creerUtilisateur("User2", "Second", "user2@test.com", "pass2", adresse, "0222222222");

        Long total = service.getTotalUtilisateurs();
        assertNotNull(total);
        assertTrue(total >= 2);
    }

    @Test
    public void testGetRepartitionParVille() {
        Adresse adresseParis = new Adresse(1, "Rue Paris", "Paris");
        Adresse adresseLyon = new Adresse(2, "Rue Lyon", "Lyon");

        service.creerUtilisateur("User1", "First", "user1@test.com", "pass1", adresseParis, "0111111111");
        service.creerUtilisateur("User2", "Second", "user2@test.com", "pass2", adresseLyon, "0222222222");
        service.creerUtilisateur("User3", "Third", "user3@test.com", "pass3", adresseParis, "0333333333");

        java.util.Map<String, Long> repartition = service.getRepartitionParVille();
        assertNotNull(repartition);
        assertTrue(repartition.containsKey("Paris"));
        assertTrue(repartition.containsKey("Lyon"));
        assertEquals(2, repartition.get("Paris"));
        assertEquals(1, repartition.get("Lyon"));
    }
}
