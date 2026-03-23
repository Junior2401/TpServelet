package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.dto.TicketDTO;
import fr.istic.taa.jaxrs.service.TicketService;
import fr.istic.taa.jaxrs.tools.tools.StatutTicket;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Path("tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {

    private final TicketService service = new TicketService();

    // -------------------------
    // GET /tickets
    // -------------------------
    @GET
    @Operation(summary = "Liste tous les tickets")
    public Response getAll() {
        List<TicketDTO> dtos = service.getAll()
                .stream()
                .map(TicketDTO::fromEntity)
                .collect(Collectors.toList());

        return Response.ok(dtos).build();
    }

    // -------------------------
    // GET /tickets/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupère un ticket par ID")
    public Response getById(@PathParam("id") Long id) {
        Ticket ticket = service.getById(id);
        if (ticket == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(TicketDTO.fromEntity(ticket)).build();
    }

    // -------------------------
    // POST /tickets
    // -------------------------
    @POST
    @Operation(summary = "Crée un ticket")
    public Response create(TicketDTO dto) {

        Ticket ticket = new Ticket();
        ticket.setNumeroPlace(dto.numeroPlace);
        ticket.setPlace(dto.place);
        ticket.setPrix(dto.prix);
        ticket.setStatut(StatutTicket.valueOf(dto.statut));

        Ticket created = service.create(ticket, dto.evenementId, dto.utilisateurId);

        return Response.status(Response.Status.CREATED)
                .entity(TicketDTO.fromEntity(created))
                .build();
    }

    // -------------------------
    // PUT /tickets/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    @Operation(summary = "Met à jour un ticket")
    public Response update(@PathParam("id") Long id, TicketDTO dto) {

        Ticket updated = service.update(id, dto);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(TicketDTO.fromEntity(updated)).build();
    }

    // -------------------------
    // DELETE /tickets/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprime un ticket")
    public Response delete(@PathParam("id") Long id) {
        Ticket existing = service.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.noContent().build();
    }

    // -------------------------
    // MÉTHODES MÉTIER
    // -------------------------

    @GET
    @Path("/utilisateur/{userId}")
    @Operation(summary = "Liste les tickets d’un utilisateur")
    public Response getByUtilisateur(@PathParam("userId") Long userId) {
        List<TicketDTO> dtos = service.getByUtilisateur(userId)
                .stream()
                .map(TicketDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/evenement/{eventId}")
    @Operation(summary = "Liste les tickets d’un événement")
    public Response getByEvenement(@PathParam("eventId") Long eventId) {
        List<TicketDTO> dtos = service.getByEvenement(eventId)
                .stream()
                .map(TicketDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/statut/{statut}")
    @Operation(summary = "Liste les tickets par statut")
    public Response getByStatut(@PathParam("statut") StatutTicket statut) {
        List<TicketDTO> dtos = service.getByStatut(statut)
                .stream()
                .map(TicketDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/purchased-after/{date}")
    @Operation(summary = "Tickets achetés après une date")
    public Response getPurchasedAfter(@PathParam("date") String date) {
        List<TicketDTO> dtos = service.getPurchasedAfter(LocalDateTime.parse(date))
                .stream()
                .map(TicketDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/cancelled-not-refunded")
    @Operation(summary = "Tickets annulés mais non remboursés")
    public Response getCancelledNotRefunded() {
        List<TicketDTO> dtos = service.getCancelledNotRefunded()
                .stream()
                .map(TicketDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/above-price/{price}")
    @Operation(summary = "Tickets dont le prix dépasse une valeur")
    public Response getAbovePrice(@PathParam("price") int price) {
        List<TicketDTO> dtos = service.getAbovePrice(price)
                .stream()
                .map(TicketDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }
}