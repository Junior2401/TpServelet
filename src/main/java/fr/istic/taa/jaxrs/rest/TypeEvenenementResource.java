package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.TypeEvenement;
import fr.istic.taa.jaxrs.service.TypeEvenementService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("type_evenements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class TypeEvenenementResource {

  private final TypeEvenementService service = new TypeEvenementService();

  // -------------------------
  // GET /type_evenements
  // -------------------------
  @GET
  public Response getAll() {
    List<TypeEvenement> list = service.getAll();
    return Response.ok(list).build();
  }

  // -------------------------
  // GET /type_evenements/{id}
  // -------------------------
  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    TypeEvenement t = service.getById(id);
    if (t == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(t).build();
  }

  // -------------------------
  // POST /type_evenements
  // -------------------------
  @POST
  public Response create(TypeEvenement typeEvenement) {
    TypeEvenement created = service.creerTypeEvenement(
            typeEvenement.getLibelle(),
            typeEvenement.getDescription()
    );
    return Response.status(Response.Status.CREATED).entity(created).build();
  }

  @PUT
  @Path("/{id}")
  public Response update(@PathParam("id") Long id, TypeEvenement updated) {
    TypeEvenement saved = service.update(id, updated);
    if (saved == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    return Response.ok(saved).build();
  }

  // -------------------------
  // DELETE /type_evenements/{id}
  // -------------------------
  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    TypeEvenement existing = service.getById(id);
    if (existing == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    service.delete(id);

    return Response.noContent().build();
  }

  // -------------------------
  // GET /type_evenements/{id}/evenements
  // -------------------------
  @GET
  @Path("/{id}/evenements")
  public Response getEvenements(@PathParam("id") Long id) {
    TypeEvenement t = service.getById(id);
    if (t == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(t.getEvenements()).build();
  }

}