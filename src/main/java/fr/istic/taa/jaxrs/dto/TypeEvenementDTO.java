package fr.istic.taa.jaxrs.dto;

import fr.istic.taa.jaxrs.domain.TypeEvenement;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO pour créer, mettre à jour et exposer un type d'événement")
public class TypeEvenementDTO {

    @Schema(description = "Identifiant unique du type d'événement", example = "1")
    public Long id;

    @Schema(description = "Libellé du type d'événement", example = "Concert")
    public String libelle;

    @Schema(description = "Description du type d'événement")
    public String description;

    public TypeEvenementDTO() {}

    public static TypeEvenementDTO fromEntity(TypeEvenement type) {
        TypeEvenementDTO dto = new TypeEvenementDTO();
        dto.id = type.getId();
        dto.libelle = type.getLibelle();
        dto.description = type.getDescription();
        return dto;
    }

    public TypeEvenement toEntity() {
        TypeEvenement t = new TypeEvenement();
        t.setId(this.id);
        t.setLibelle(this.libelle);
        t.setDescription(this.description);
        return t;
    }
}

