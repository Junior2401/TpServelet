package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.service.ArtisteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("artistes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtisteResource {

    private final ArtisteService service = new ArtisteService();

    // -------------------------
    // GET /artistes
    // -------------------------
    @GET
    public Response getAll() {
        List<Artiste> list = service.getAll();
        return Response.ok(list).build();
    }

    // -------------------------
    // GET /artistes/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Artiste artiste = service.getById(id);
        if (artiste == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(artiste).build();
    }

    // -------------------------
    // POST /artistes
    // -------------------------
    @POST
    public Response create(Artiste artiste) {
        Artiste created = service.creerArtiste(
                artiste.getNom(),
                artiste.getPrenom(),
                artiste.getEmail(),
                artiste.getPassword(),
                artiste.getAdresse(),
                artiste.getNomDeScene(),
                artiste.getStyleArtistique()
        );
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // -------------------------
    // PUT /artistes/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Artiste updated) {
        Artiste saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(saved).build();
    }

    // -------------------------
    // DELETE /artistes/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Artiste existing = service.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.noContent().build();
    }

    // -------------------------
    // GET /artistes/{id}/evenements
    // -------------------------
    @GET
    @Path("/{id}/evenements")
    public Response getEvenements(@PathParam("id") Long id) {
        Artiste artiste = service.getById(id);
        if (artiste == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(artiste.getEvenements()).build();
    }
}

