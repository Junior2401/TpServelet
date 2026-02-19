package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.service.TicketService;
import fr.istic.taa.jaxrs.tools.tools;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {

    private final TicketService service = new TicketService();

    // -------------------------
    // GET /tickets
    // -------------------------
    @GET
    public Response getAll() {
        List<Ticket> list = service.getAll();
        return Response.ok(list).build();
    }

    // -------------------------
    // GET /tickets/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Ticket ticket = service.getById(id);
        if (ticket == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ticket).build();
    }

    // -------------------------
    // POST /tickets
    // -------------------------
    @POST
    public Response create(Ticket ticket) {
        Ticket created = service.creerTicket(
                ticket.getNumeroPlace(),
                ticket.getPlace(),
                ticket.getStatut(),
                ticket.getPrix(),
                ticket.getDateAchat(),
                ticket.getDateAnnulation(),
                ticket.getDateRemboursement(),
                ticket.getEvenement().getId().intValue(),
                ticket.getUtilisateur().getId().intValue()
        );
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // -------------------------
    // PUT /tickets/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Ticket updated) {
        Ticket saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(saved).build();
    }

    // -------------------------
    // DELETE /tickets/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Ticket existing = service.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.noContent().build();
    }
}

