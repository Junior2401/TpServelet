package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.dto.ArtisteDTO;
import fr.istic.taa.jaxrs.service.ArtisteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("artistes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Artistes")
public class ArtisteResource {

    private final ArtisteService service = new ArtisteService();

    // -------------------------
    // GET /artistes
    // -------------------------
    @GET
    @Operation(summary = "Liste tous les artistes")
    public Response getAll() {
        List<ArtisteDTO> dtos = service.getAll()
                .stream()
                .map(ArtisteDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    // -------------------------
    // GET /artistes/{id}
    // -------------------------
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupère un artiste par ID")
    public Response getById(@PathParam("id") Long id) {
        Artiste artiste = service.getById(id);
        if (artiste == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ArtisteDTO.fromEntity(artiste)).build();
    }

    // -------------------------
    // POST /artistes
    // -------------------------
    @POST
    @Operation(summary = "Crée un nouvel artiste")
    public Response create(ArtisteDTO dto) {
        Artiste artiste = new Artiste();
        artiste.setNom(dto.nom);
        artiste.setPrenom(dto.prenom);
        artiste.setEmail(dto.email);
        artiste.setPassword(dto.password);
        artiste.setNomDeScene(dto.nomDeScene);
        artiste.setStyleArtistique(dto.styleArtistique);

        Artiste created = service.creerArtiste(
                artiste.getNom(),
                artiste.getPrenom(),
                artiste.getEmail(),
                artiste.getPassword(),
                artiste.getAdresse(),
                artiste.getNomDeScene(),
                artiste.getStyleArtistique()
        );
        return Response.status(Response.Status.CREATED).entity(ArtisteDTO.fromEntity(created)).build();
    }

    // -------------------------
    // PUT /artistes/{id}
    // -------------------------
    @PUT
    @Path("/{id}")
    @Operation(summary = "Met à jour un artiste")
    public Response update(@PathParam("id") Long id, ArtisteDTO dto) {
        Artiste updated = new Artiste();
        updated.setNom(dto.nom);
        updated.setPrenom(dto.prenom);
        updated.setEmail(dto.email);
        updated.setPassword(dto.password);
        updated.setNomDeScene(dto.nomDeScene);
        updated.setStyleArtistique(dto.styleArtistique);

        Artiste saved = service.update(id, updated);
        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ArtisteDTO.fromEntity(saved)).build();
    }

    // -------------------------
    // DELETE /artistes/{id}
    // -------------------------
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprime un artiste")
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
    @Operation(summary = "Liste les événements d'un artiste")
    public Response getEvenements(@PathParam("id") Long id) {
        Artiste artiste = service.getById(id);
        if (artiste == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(artiste.getEvenements()).build();
    }
}

