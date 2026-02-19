package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Personne;
import fr.istic.taa.jaxrs.service.PersonneService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("personnes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonneResource {

    private final PersonneService service = new PersonneService();

    // -------------------------
    // GET /personnes
    // -------------------------
    @GET
    public Response getAll() {
        List<Personne> list = service.getAll();
        return Response.ok(list).build();
    }

    // -------------------------
    // GET /personnes/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Personne personne = service.getById(id);
        if (personne == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(personne).build();
    }

    // -------------------------
    // PUT /personnes/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Personne updated) {
        Personne saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(saved).build();
    }

    // -------------------------
    // DELETE /personnes/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Personne existing = service.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.noContent().build();
    }
}

