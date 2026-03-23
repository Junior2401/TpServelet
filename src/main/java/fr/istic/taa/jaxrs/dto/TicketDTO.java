package fr.istic.taa.jaxrs.dto;

import fr.istic.taa.jaxrs.domain.Ticket;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Schema(description = "DTO unique pour créer, mettre à jour et exposer un ticket")
public class TicketDTO {

    // -------------------------
    // Champs principaux
    // -------------------------

    @Schema(description = "Identifiant unique du ticket", example = "12")
    public Long id;

    @Schema(description = "Numéro unique de la place", example = "A15")
    public String numeroPlace;

    @Schema(description = "Emplacement de la place", example = "Balcon")
    public String place;

    @Schema(description = "Prix du ticket", example = "45")
    public Integer prix;

    @Schema(description = "Statut du ticket", example = "ACHETE")
    public String statut;

    @Schema(description = "Date d'achat du ticket")
    public LocalDateTime dateAchat;

    @Schema(description = "Date d'annulation du ticket")
    public LocalDateTime dateAnnulation;

    @Schema(description = "Date de remboursement du ticket")
    public LocalDateTime dateRemboursement;

    // -------------------------
    // Relations (IDs)
    // -------------------------

    @Schema(description = "ID de l'événement associé", example = "3")
    public Long evenementId;

    @Schema(description = "ID de l'utilisateur associé", example = "7")
    public Long utilisateurId;

    // -------------------------
    // Champs enrichis (lecture seule)
    // -------------------------

    @Schema(description = "Libellé de l'événement", example = "Concert de Youssou N'Dour")
    public String evenementLibelle;

    @Schema(description = "Nom complet de l'utilisateur", example = "Dupont Jean")
    public String utilisateurNomComplet;

    // -------------------------
    // Constructeur vide
    // -------------------------

    public TicketDTO() {}

    // -------------------------
    // Conversion entité → DTO
    // -------------------------

    public static TicketDTO fromEntity(Ticket t) {
        if (t == null) return null;

        TicketDTO dto = new TicketDTO();

        dto.id = t.getId();
        dto.numeroPlace = t.getNumeroPlace();
        dto.place = t.getPlace();
        dto.prix = t.getPrix();
        dto.statut = t.getStatut().name();
        dto.dateAchat = t.getDateAchat();
        dto.dateAnnulation = t.getDateAnnulation();
        dto.dateRemboursement = t.getDateRemboursement();

        dto.evenementId = t.getEvenement() != null ? t.getEvenement().getId() : null;
        dto.utilisateurId = t.getUtilisateur() != null ? t.getUtilisateur().getId() : null;

        dto.evenementLibelle = t.getEvenement() != null ? t.getEvenement().getLibelle() : null;
        dto.utilisateurNomComplet = t.getUtilisateur() != null
                ? t.getUtilisateur().getNom() + " " + t.getUtilisateur().getPrenom()
                : null;

        return dto;
    }
}