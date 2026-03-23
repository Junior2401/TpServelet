package fr.istic.taa.jaxrs.dto;

import fr.istic.taa.jaxrs.domain.Artiste;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO pour créer, mettre à jour et exposer un artiste")
public class ArtisteDTO extends PersonneDTO {

    @Schema(description = "Nom de scène de l'artiste", example = "The Great Artist")
    public String nomDeScene;

    @Schema(description = "Style artistique de l'artiste", example = "Rock, Pop")
    public String styleArtistique;

    public ArtisteDTO() {}

    public static ArtisteDTO fromEntity(Artiste artiste) {
        ArtisteDTO dto = new ArtisteDTO();
        dto.id = artiste.getId();
        dto.nom = artiste.getNom();
        dto.prenom = artiste.getPrenom();
        dto.email = artiste.getEmail();
        dto.password = artiste.getPassword();
        dto.nomDeScene = artiste.getNomDeScene();
        dto.styleArtistique = artiste.getStyleArtistique();
        return dto;
    }

    public Artiste toEntity() {
        Artiste a = new Artiste();
        a.setId(this.id);
        a.setNom(this.nom);
        a.setPrenom(this.prenom);
        a.setEmail(this.email);
        a.setPassword(this.password);
        a.setNomDeScene(this.nomDeScene);
        a.setStyleArtistique(this.styleArtistique);
        return a;
    }
}

