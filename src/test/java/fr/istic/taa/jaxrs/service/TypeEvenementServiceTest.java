package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.domain.TypeEvenement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TypeEvenementServiceTest {

    private TypeEvenementService service;

    @BeforeEach
    public void setUp() {
        service = new TypeEvenementService();
    }

    @Test
    public void testCreerTypeEvenement() {
        TypeEvenement type = service.creerTypeEvenement(
                "Concert",
                "Un concert musical"
        );

        assertNotNull(type);
        assertEquals("Concert", type.getLibelle());
        assertEquals("Un concert musical", type.getDescription());
    }

    @Test
    public void testGetAll() {
        service.creerTypeEvenement("Concert", "Music event");
        service.creerTypeEvenement("Théâtre", "Theater event");

        List<TypeEvenement> types = service.getAll();
        assertNotNull(types);
        assertTrue(types.size() >= 2);
    }

    @Test
    public void testGetById() {
        TypeEvenement created = service.creerTypeEvenement(
                "Festival",
                "Un festival"
        );

        assertNotNull(created.getId());
        TypeEvenement retrieved = service.getById(created.getId());
        assertNotNull(retrieved);
        assertEquals("Festival", retrieved.getLibelle());
    }

    @Test
    public void testUpdate() {
        TypeEvenement created = service.creerTypeEvenement(
                "Spectacle",
                "Un spectacle"
        );

        TypeEvenement updated = new TypeEvenement();
        updated.setLibelle("Spectacle Musical");
        updated.setDescription("Un spectacle musical amélioré");

        TypeEvenement result = service.update(created.getId(), updated);
        assertNotNull(result);
        assertEquals("Spectacle Musical", result.getLibelle());
        assertEquals("Un spectacle musical amélioré", result.getDescription());
    }

    @Test
    public void testDelete() {
        TypeEvenement created = service.creerTypeEvenement(
                "Conférence",
                "Une conférence"
        );

        Long id = created.getId();
        service.delete(id);

        TypeEvenement retrieved = service.getById(id);
        assertNull(retrieved);
    }

    @Test
    public void testGetByIdNotFound() {
        TypeEvenement type = service.getById(99999L);
        assertNull(type);
    }
}

