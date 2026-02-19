package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Administrateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.service.AdministrateurService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("administrateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministrateurResource {

    private final AdministrateurService service = new AdministrateurService();

    // -------------------------
    // GET /administrateurs
    // -------------------------
    @GET
    public Response getAll() {
        List<Administrateur> list = service.getAll();
        return Response.ok(list).build();
    }

    // -------------------------
    // GET /administrateurs/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Administrateur admin = service.getById(id);
        if (admin == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(admin).build();
    }

    // -------------------------
    // POST /administrateurs
    // -------------------------
    @POST
    public Response create(Administrateur administrateur) {
        Administrateur created = service.creerAdministrateur(
                administrateur.getNom(),
                administrateur.getPrenom(),
                administrateur.getEmail(),
                administrateur.getPassword(),
                administrateur.getAdresse(),
                administrateur.getRole()
        );
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // -------------------------
    // PUT /administrateurs/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Administrateur updated) {
        Administrateur saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(saved).build();
    }

    // -------------------------
    // DELETE /administrateurs/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Administrateur existing = service.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.noContent().build();
    }
}

