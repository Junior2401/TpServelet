package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.dto.UtilisateurDTO;
import fr.istic.taa.jaxrs.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Utilisateurs")
public class UtilisateurResource {

    private final UtilisateurService service = new UtilisateurService();

    // -------------------------
    // GET /utilisateurs
    // -------------------------
    @GET
    @Operation(summary = "Liste tous les utilisateurs")
    public Response getAll() {
        List<UtilisateurDTO> dtos = service.getAll()
                .stream()
                .map(UtilisateurDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    // -------------------------
    // GET /utilisateurs/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupère un utilisateur par ID")
    public Response getById(@PathParam("id") Long id) {
        Utilisateur utilisateur = service.getById(id);
        if (utilisateur == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(UtilisateurDTO.fromEntity(utilisateur)).build();
    }

    // -------------------------
    // POST /utilisateurs
    // -------------------------
    @POST
    @Operation(summary = "Crée un nouvel utilisateur")
    public Response create(UtilisateurDTO dto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(dto.nom);
        utilisateur.setPrenom(dto.prenom);
        utilisateur.setEmail(dto.email);
        utilisateur.setPassword(dto.password);
        utilisateur.setTelephone(dto.telephone);

        Utilisateur created = service.creerUtilisateur(
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                utilisateur.getAdresse(),
                utilisateur.getTelephone()
        );
        return Response.status(Response.Status.CREATED).entity(UtilisateurDTO.fromEntity(created)).build();
    }

    // -------------------------
    // PUT /utilisateurs/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    @Operation(summary = "Met à jour un utilisateur")
    public Response update(@PathParam("id") Long id, UtilisateurDTO dto) {
        Utilisateur updated = new Utilisateur();
        updated.setNom(dto.nom);
        updated.setPrenom(dto.prenom);
        updated.setEmail(dto.email);
        updated.setPassword(dto.password);
        updated.setTelephone(dto.telephone);

        Utilisateur saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(UtilisateurDTO.fromEntity(saved)).build();
    }

    // -------------------------
    // DELETE /utilisateurs/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprime un utilisateur")
    public Response delete(@PathParam("id") Long id) {
        Utilisateur existing = service.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.noContent().build();
    }

    // -------------------------
    // GET /utilisateurs/{id}/tickets
    // -------------------------
    @GET
    @Path("/{id}/tickets")
    public Response getTickets(@PathParam("id") Long id) {
        Utilisateur utilisateur = service.getById(id);
        if (utilisateur == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(utilisateur.getTickets()).build();
    }
}

