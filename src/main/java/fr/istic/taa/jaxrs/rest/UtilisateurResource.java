package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.service.UtilisateurService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurResource {

    private final UtilisateurService service = new UtilisateurService();

    // -------------------------
    // GET /utilisateurs
    // -------------------------
    @GET
    public Response getAll() {
        List<Utilisateur> list = service.getAll();
        return Response.ok(list).build();
    }

    // -------------------------
    // GET /utilisateurs/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Utilisateur utilisateur = service.getById(id);
        if (utilisateur == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(utilisateur).build();
    }

    // -------------------------
    // POST /utilisateurs
    // -------------------------
    @POST
    public Response create(Utilisateur utilisateur) {
        Utilisateur created = service.creerUtilisateur(
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                utilisateur.getAdresse(),
                utilisateur.getTelephone()
        );
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // -------------------------
    // PUT /utilisateurs/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Utilisateur updated) {
        Utilisateur saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(saved).build();
    }

    // -------------------------
    // DELETE /utilisateurs/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
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

