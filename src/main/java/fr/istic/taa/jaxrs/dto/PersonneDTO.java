package fr.istic.taa.jaxrs.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de base pour les personnes (abstract)")
public abstract class PersonneDTO {

    @Schema(description = "Identifiant unique de la personne", example = "1")
    public Long id;

    @Schema(description = "Nom de la personne", example = "Dupont")
    public String nom;

    @Schema(description = "Prénom de la personne", example = "Jean")
    public String prenom;

    @Schema(description = "Email de la personne", example = "jean.dupont@example.com")
    public String email;

    @Schema(description = "Mot de passe de la personne")
    public String password;
}

