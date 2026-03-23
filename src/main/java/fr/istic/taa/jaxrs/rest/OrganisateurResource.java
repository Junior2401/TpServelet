package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.dto.OrganisateurDTO;
import fr.istic.taa.jaxrs.service.OrganisateurService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("organisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganisateurResource {

    private final OrganisateurService service = new OrganisateurService();

    // -------------------------
    // GET /organisateurs
    // -------------------------
    @GET
    @Operation(summary = "Liste tous les organisateurs")
    public Response getAll() {
        List<OrganisateurDTO> dtos = service.getAll()
                .stream()
                .map(OrganisateurDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    // -------------------------
    // GET /organisateurs/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupère un organisateur par ID")
    public Response getById(@PathParam("id") Long id) {
        Organisateur organisateur = service.getById(id);
        if (organisateur == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(OrganisateurDTO.fromEntity(organisateur)).build();
    }

    // -------------------------
    // POST /organisateurs
    // -------------------------
    @POST
    @Operation(summary = "Crée un nouvel organisateur")
    public Response create(OrganisateurDTO dto) {
        Organisateur organisateur = new Organisateur();
        organisateur.setNom(dto.nom);
        organisateur.setPrenom(dto.prenom);
        organisateur.setEmail(dto.email);
        organisateur.setPassword(dto.password);
        organisateur.setSociete(dto.societe);
        organisateur.setTelephonePro(dto.telephonePro);

        Organisateur created = service.creerOrganisateur(
                organisateur.getNom(),
                organisateur.getPrenom(),
                organisateur.getEmail(),
                organisateur.getPassword(),
                organisateur.getAdresse(),
                organisateur.getSociete(),
                organisateur.getTelephonePro()
        );
        return Response.status(Response.Status.CREATED).entity(OrganisateurDTO.fromEntity(created)).build();
    }

    // -------------------------
    // PUT /organisateurs/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    @Operation(summary = "Met à jour un organisateur")
    public Response update(@PathParam("id") Long id, OrganisateurDTO dto) {
        Organisateur updated = new Organisateur();
        updated.setNom(dto.nom);
        updated.setPrenom(dto.prenom);
        updated.setEmail(dto.email);
        updated.setPassword(dto.password);
        updated.setSociete(dto.societe);
        updated.setTelephonePro(dto.telephonePro);

        Organisateur saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(OrganisateurDTO.fromEntity(saved)).build();
    }

    // -------------------------
    // DELETE /organisateurs/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprime un organisateur")
    public Response delete(@PathParam("id") Long id) {
        Organisateur existing = service.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.noContent().build();
    }

    // -------------------------
    // GET /organisateurs/{id}/evenements
    // -------------------------
    @GET
    @Path("/{id}/evenements")
    @Operation(summary = "Liste les événements d'un organisateur")
    public Response getEvenements(@PathParam("id") Long id) {
        Organisateur organisateur = service.getById(id);
        if (organisateur == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(organisateur.getEvenements()).build();
    }
}

