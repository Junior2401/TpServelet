package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.dto.EvenementDTO;
import fr.istic.taa.jaxrs.service.EvenementService;
import fr.istic.taa.jaxrs.tools.tools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Path("evenements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Evenements")
public class EvenementResource {

    private final EvenementService service = new EvenementService();

    // -------------------------
    // GET /evenements
    // -------------------------
    @GET
    @Operation(summary = "Liste tous les événements")
    public Response getAll() {
        List<EvenementDTO> dtos = service.getAll()
                .stream()
                .map(EvenementDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    // -------------------------
    // GET /evenements/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupère un événement par ID")
    public Response getById(@PathParam("id") Long id) {
        Evenement evenement = service.getById(id);
        if (evenement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(EvenementDTO.fromEntity(evenement)).build();
    }

    // -------------------------
    // POST /evenements
    // -------------------------
    @POST
    @Operation(summary = "Crée un nouvel événement")
    public Response create(EvenementDTO dto) {
        Evenement evenement = new Evenement();
        evenement.setLibelle(dto.libelle);
        evenement.setLieu(dto.lieu);
        evenement.setDate(dto.date);
        evenement.setCapacite(dto.capacite);
        evenement.setDescription(dto.description);
        if (dto.statut != null) {
            evenement.setStatut(tools.StatutEvenement.valueOf(dto.statut));
        }

        Evenement created = service.creerEvenement(
                dto.libelle,
                dto.lieu,
                dto.date,
                dto.capacite,
                dto.description,
                dto.statut != null ? tools.StatutEvenement.valueOf(dto.statut) : tools.StatutEvenement.CREE,
                dto.typeEvenementId
        );
        return Response.status(Response.Status.CREATED).entity(EvenementDTO.fromEntity(created)).build();
    }

    // -------------------------
    // PUT /evenements/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    @Operation(summary = "Met à jour un événement")
    public Response update(@PathParam("id") Long id, EvenementDTO dto) {
        Evenement updated = new Evenement();
        updated.setLibelle(dto.libelle);
        updated.setLieu(dto.lieu);
        updated.setDate(dto.date);
        updated.setCapacite(dto.capacite);
        updated.setDescription(dto.description);
        if (dto.statut != null) {
            updated.setStatut(tools.StatutEvenement.valueOf(dto.statut));
        }

        Evenement saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(EvenementDTO.fromEntity(saved)).build();
    }

    // -------------------------
    // DELETE /evenements/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprime un événement")
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
    @Operation(summary = "Liste les organisateurs d'un événement")
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
    @Operation(summary = "Liste les artistes d'un événement")
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
    @Operation(summary = "Liste les tickets d'un événement")
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
    @Operation(summary = "Ajoute un organisateur à un événement")
    public Response ajouterOrganisateur(@PathParam("id") Long id, @PathParam("organisateurId") Long organisateurId) {
        Evenement updated = service.ajouterOrganisateur(id, organisateurId);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(EvenementDTO.fromEntity(updated)).build();
    }

    // -------------------------
    // POST /evenements/{id}/artistes/{artisteId}
    // -------------------------
    @POST
    @Path("/{id}/artistes/{artisteId}")
    @Operation(summary = "Ajoute un artiste à un événement")
    public Response ajouterArtiste(@PathParam("id") Long id, @PathParam("artisteId") Long artisteId) {
        Evenement updated = service.ajouterArtiste(id, artisteId);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(EvenementDTO.fromEntity(updated)).build();
    }
}
