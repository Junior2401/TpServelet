package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.service.EvenementService;
import fr.istic.taa.jaxrs.tools.tools;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("evenements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EvenementResource {

    private final EvenementService service = new EvenementService();

    // -------------------------
    // GET /evenements
    // -------------------------
    @GET
    public Response getAll() {
        List<Evenement> list = service.getAll();
        return Response.ok(list).build();
    }

    // -------------------------
    // GET /evenements/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Evenement evenement = service.getById(id);
        if (evenement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(evenement).build();
    }

    // -------------------------
    // POST /evenements
    // -------------------------
    @POST
    public Response create(Evenement evenement) {
        Evenement created = service.creerEvenement(
                evenement.getLibelle(),
                evenement.getLieu(),
                evenement.getDate(),
                evenement.getCapacite(),
                evenement.getDescription(),
                evenement.getStatut(),
                evenement.getTypeEvenement().getId().intValue()
        );
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // -------------------------
    // PUT /evenements/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Evenement updated) {
        Evenement saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(saved).build();
    }

    // -------------------------
    // DELETE /evenements/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Evenement existing = service.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.noContent().build();
    }

    // -------------------------
    // GET /evenements/{id}/organisateurs
    // -------------------------
    @GET
    @Path("/{id}/organisateurs")
    public Response getOrganisateurs(@PathParam("id") Long id) {
        Evenement evenement = service.getById(id);
        if (evenement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(evenement.getOrganisateurs()).build();
    }

    // -------------------------
    // GET /evenements/{id}/artistes
    // -------------------------
    @GET
    @Path("/{id}/artistes")
    public Response getArtistes(@PathParam("id") Long id) {
        Evenement evenement = service.getById(id);
        if (evenement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(evenement.getArtistes()).build();
    }

    // -------------------------
    // GET /evenements/{id}/tickets
    // -------------------------
    @GET
    @Path("/{id}/tickets")
    public Response getTickets(@PathParam("id") Long id) {
        Evenement evenement = service.getById(id);
        if (evenement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(evenement.getTickets()).build();
    }

    // -------------------------
    // POST /evenements/{id}/organisateurs/{organisateurId}
    // -------------------------
    @POST
    @Path("/{id}/organisateurs/{organisateurId}")
    public Response ajouterOrganisateur(@PathParam("id") Long id, @PathParam("organisateurId") Long organisateurId) {
        Evenement updated = service.ajouterOrganisateur(id.intValue(), organisateurId.intValue());
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    // -------------------------
    // POST /evenements/{id}/artistes/{artisteId}
    // -------------------------
    @POST
    @Path("/{id}/artistes/{artisteId}")
    public Response ajouterArtiste(@PathParam("id") Long id, @PathParam("artisteId") Long artisteId) {
        Evenement updated = service.ajouterArtiste(id.intValue(), artisteId.intValue());
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }
}

