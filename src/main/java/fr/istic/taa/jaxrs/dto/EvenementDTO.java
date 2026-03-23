package fr.istic.taa.jaxrs.dto;

import fr.istic.taa.jaxrs.domain.Evenement;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO pour créer, mettre à jour et exposer un événement")
public class EvenementDTO {

    @Schema(description = "Identifiant unique de l'événement", example = "1")
    public Long id;

    @Schema(description = "Libellé de l'événement", example = "Concert 2026")
    public String libelle;

    @Schema(description = "Lieu de l'événement", example = "Paris, Stade de France")
    public String lieu;

    @Schema(description = "Date et heure de l'événement")
    public LocalDateTime date;

    @Schema(description = "Capacité d'accueil", example = "50000")
    public Integer capacite;

    @Schema(description = "Description de l'événement")
    public String description;

    @Schema(description = "Statut de l'événement", example = "PLANIFIE")
    public String statut;

    @Schema(description = "ID du type d'événement associé", example = "1")
    public Long typeEvenementId;

    public EvenementDTO() {}

    public static EvenementDTO fromEntity(Evenement evenement) {
        EvenementDTO dto = new EvenementDTO();
        dto.id = evenement.getId();
        dto.libelle = evenement.getLibelle();
        dto.lieu = evenement.getLieu();
        dto.date = evenement.getDate();
        dto.capacite = evenement.getCapacite();
        dto.description = evenement.getDescription();
        if (evenement.getStatut() != null) {
            dto.statut = evenement.getStatut().toString();
        }
        if (evenement.getTypeEvenement() != null) {
            dto.typeEvenementId = evenement.getTypeEvenement().getId();
        }
        return dto;
    }

    public Evenement toEntity() {
        Evenement e = new Evenement();
        e.setId(this.id);
        e.setLibelle(this.libelle);
        e.setLieu(this.lieu);
        e.setDate(this.date);
        e.setCapacite(this.capacite);
        e.setDescription(this.description);
        return e;
    }
}

