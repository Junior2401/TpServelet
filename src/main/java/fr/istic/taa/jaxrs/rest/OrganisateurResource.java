package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.service.OrganisateurService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("organisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganisateurResource {

    private final OrganisateurService service = new OrganisateurService();

    // -------------------------
    // GET /organisateurs
    // -------------------------
    @GET
    public Response getAll() {
        List<Organisateur> list = service.getAll();
        return Response.ok(list).build();
    }

    // -------------------------
    // GET /organisateurs/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Organisateur organisateur = service.getById(id);
        if (organisateur == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(organisateur).build();
    }

    // -------------------------
    // POST /organisateurs
    // -------------------------
    @POST
    public Response create(Organisateur organisateur) {
        Organisateur created = service.creerOrganisateur(
                organisateur.getNom(),
                organisateur.getPrenom(),
                organisateur.getEmail(),
                organisateur.getPassword(),
                organisateur.getAdresse(),
                organisateur.getSociete(),
                organisateur.getTelephonePro()
        );
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // -------------------------
    // PUT /organisateurs/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Organisateur updated) {
        Organisateur saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(saved).build();
    }

    // -------------------------
    // DELETE /organisateurs/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
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
    public Response getEvenements(@PathParam("id") Long id) {
        Organisateur organisateur = service.getById(id);
        if (organisateur == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(organisateur.getEvenements()).build();
    }
}

