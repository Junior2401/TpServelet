package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Administrateur;
import fr.istic.taa.jaxrs.dto.AdministrateurDTO;
import fr.istic.taa.jaxrs.service.AdministrateurService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("administrateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministrateurResource {

    private final AdministrateurService service = new AdministrateurService();

    // -------------------------
    // GET /administrateurs
    // -------------------------
    @GET
    @Operation(summary = "Liste tous les administrateurs")
    public Response getAll() {
        List<AdministrateurDTO> dtos = service.getAll()
                .stream()
                .map(AdministrateurDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    // -------------------------
    // GET /administrateurs/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupère un administrateur par ID")
    public Response getById(@PathParam("id") Long id) {
        Administrateur admin = service.getById(id);
        if (admin == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(AdministrateurDTO.fromEntity(admin)).build();
    }

    // -------------------------
    // POST /administrateurs
    // -------------------------
    @POST
    @Operation(summary = "Crée un nouvel administrateur")
    public Response create(AdministrateurDTO dto) {
        Administrateur administrateur = new Administrateur();
        administrateur.setNom(dto.nom);
        administrateur.setPrenom(dto.prenom);
        administrateur.setEmail(dto.email);
        administrateur.setPassword(dto.password);
        administrateur.setRole(dto.role);

        Administrateur created = service.creerAdministrateur(
                administrateur.getNom(),
                administrateur.getPrenom(),
                administrateur.getEmail(),
                administrateur.getPassword(),
                administrateur.getAdresse(),
                administrateur.getRole()
        );
        return Response.status(Response.Status.CREATED).entity(AdministrateurDTO.fromEntity(created)).build();
    }

    // -------------------------
    // PUT /administrateurs/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    @Operation(summary = "Met à jour un administrateur")
    public Response update(@PathParam("id") Long id, AdministrateurDTO dto) {
        Administrateur updated = new Administrateur();
        updated.setNom(dto.nom);
        updated.setPrenom(dto.prenom);
        updated.setEmail(dto.email);
        updated.setPassword(dto.password);
        updated.setRole(dto.role);

        Administrateur saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(AdministrateurDTO.fromEntity(saved)).build();
    }

    // -------------------------
    // DELETE /administrateurs/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprime un administrateur")
    public Response delete(@PathParam("id") Long id) {
        Administrateur existing = service.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.noContent().build();
    }
}

