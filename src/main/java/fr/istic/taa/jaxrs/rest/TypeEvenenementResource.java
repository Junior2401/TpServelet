package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.TypeEvenement;
import fr.istic.taa.jaxrs.dto.TypeEvenementDTO;
import fr.istic.taa.jaxrs.service.TypeEvenementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("type_evenements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Types Evenements")
public class TypeEvenenementResource {

  private final TypeEvenementService service = new TypeEvenementService();

  // -------------------------
  // GET /type_evenements
  // -------------------------
  @GET
  @Operation(summary = "Liste tous les types d'événements")
  public Response getAll() {
    List<TypeEvenementDTO> dtos = service.getAll()
            .stream()
            .map(TypeEvenementDTO::fromEntity)
            .collect(Collectors.toList());
    return Response.ok(dtos).build();
  }

  // -------------------------
  // GET /type_evenements/{id}
  // -------------------------
  @GET
  @Path("/{id}")
  @Operation(summary = "Récupère un type d'événement par ID")
  public Response getById(@PathParam("id") Long id) {
    TypeEvenement t = service.getById(id);
    if (t == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(TypeEvenementDTO.fromEntity(t)).build();
  }

  // -------------------------
  // POST /type_evenements
  // -------------------------
  @POST
  @Operation(summary = "Crée un nouveau type d'événement")
  public Response create(TypeEvenementDTO dto) {
    TypeEvenement typeEvenement = new TypeEvenement();
    typeEvenement.setLibelle(dto.libelle);
    typeEvenement.setDescription(dto.description);

    TypeEvenement created = service.creerTypeEvenement(
            typeEvenement.getLibelle(),
            typeEvenement.getDescription()
    );
    return Response.status(Response.Status.CREATED).entity(TypeEvenementDTO.fromEntity(created)).build();
  }

  // -------------------------
  // PUT /type_evenements/{id}
  // -------------------------
  @PUT
  @Path("/{id}")
  @Operation(summary = "Met à jour un type d'événement")
  public Response update(@PathParam("id") Long id, TypeEvenementDTO dto) {
    TypeEvenement updated = new TypeEvenement();
    updated.setLibelle(dto.libelle);
    updated.setDescription(dto.description);

    TypeEvenement saved = service.update(id, updated);
    if (saved == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(TypeEvenementDTO.fromEntity(saved)).build();
  }

  // -------------------------
  // DELETE /type_evenements/{id}
  // -------------------------
  @DELETE
  @Path("/{id}")
  @Operation(summary = "Supprime un type d'événement")
  public Response delete(@PathParam("id") Long id) {
    TypeEvenement existing = service.getById(id);
    if (existing == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    service.delete(existing);
    return Response.noContent().build();
  }

  // -------------------------
  // GET /type_evenements/{id}/evenements
  // -------------------------
  @GET
  @Path("/{id}/evenements")
  @Operation(summary = "Liste les événements d'un type")
  public Response getEvenements(@PathParam("id") Long id) {
    TypeEvenement t = service.getById(id);
    if (t == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(t.getEvenements()).build();
  }

}