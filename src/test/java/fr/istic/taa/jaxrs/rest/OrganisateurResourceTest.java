package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.service.OrganisateurService;
import fr.istic.taa.jaxrs.dto.OrganisateurDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class OrganisateurResourceTest {

    private OrganisateurResource resource;
    private OrganisateurService service;
    private Adresse adresse;

    @BeforeEach
    public void setUp() {
        resource = new OrganisateurResource();
        service = new OrganisateurService();
        adresse = new Adresse(321, "Rue de l'Organisation", "Toulouse");
    }

    @Test
    public void testGetAll() {
        Response response = resource.getAll();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetById() {
        Organisateur org = service.creerOrganisateur(
                "TestOrg",
                "Test",
                "test.org@test.com",
                "password",
                adresse,
                "TestCompany",
                "0999999999"
        );

        Response response = resource.getById(org.getId());
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
        OrganisateurDTO dto = new OrganisateurDTO();
        dto.nom = "NewOrg";
        dto.prenom = "New";
        dto.email = "new@org.com";
        dto.password = "password";
        dto.societe = "NewCompany";
        dto.telephonePro = "0123456789";

        Response response = resource.create(dto);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdate() {
        Organisateur org = service.creerOrganisateur(
                "ToUpdate",
                "Update",
                "update.org@test.com",
                "password",
                adresse,
                "UpdateCompany",
                "0111111111"
        );

        OrganisateurDTO dto = new OrganisateurDTO();
        dto.societe = "NewCompany";
        dto.telephonePro = "0777777777";

        Response response = resource.update(org.getId(), dto);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNotFound() {
        OrganisateurDTO dto = new OrganisateurDTO();
        dto.societe = "NewCompany";

        Response response = resource.update(99999L, dto);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDelete() {
        Organisateur org = service.creerOrganisateur(
                "ToDelete",
                "Delete",
                "delete.org@test.com",
                "password",
                adresse,
                "DeleteCompany",
                "0333333333"
        );

        Response response = resource.delete(org.getId());
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
    public void testGetEvenements() {
        Organisateur org = service.creerOrganisateur(
                "OrgWithEvents",
                "Events",
                "events@org.com",
                "password",
                adresse,
                "EventCompany",
                "0111111111"
        );

        Response response = resource.getEvenements(org.getId());
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetEvenementsNotFound() {
        Response response = resource.getEvenements(99999L);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
