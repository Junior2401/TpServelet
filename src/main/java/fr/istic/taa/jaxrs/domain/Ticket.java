package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fr.istic.taa.jaxrs.tools.tools;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.time.LocalDateTime;


@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@NamedQueries({
        @NamedQuery(
                name = "Ticket.findByUtilisateur",
                query = "SELECT t FROM Ticket t WHERE t.utilisateur.id = :userId"
        ),
        @NamedQuery(
                name = "Ticket.findByEvenement",
                query = "SELECT t FROM Ticket t WHERE t.evenement.id = :eventId"
        ),
        @NamedQuery(
                name = "Ticket.findByStatut",
                query = "SELECT t FROM Ticket t WHERE t.statut = :statut"
        ),
        @NamedQuery(
                name = "Ticket.findPurchasedAfter",
                query = "SELECT t FROM Ticket t WHERE t.dateAchat >= :dateMin"
        ),
        @NamedQuery(
                name = "Ticket.findCancelledNotRefunded",
                query = "SELECT t FROM Ticket t WHERE t.dateAnnulation IS NOT NULL AND t.dateRemboursement IS NULL"
        )
})

@Schema(description = "Représente les ticket des évènements en cours")
@Entity
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Positive
    @Schema(description = "Numéro unique du ticket")
    private String numeroPlace;

    @NotBlank
    private String place;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private tools.StatutTicket statut;

    @NotNull
    @Positive
    private Integer prix;

    @Column(nullable = false)
    private LocalDateTime dateAchat;

    @Column(nullable = true)
    private LocalDateTime dateAnnulation;

    @Column(nullable = true)
    private LocalDateTime dateRemboursement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evenement_id")
    private Evenement evenement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", numeroPlace='" + numeroPlace + '\'' +
                ", place='" + place + '\'' +
                ", statut=" + statut +
                ", prix=" + prix +
                ", dateAchat=" + dateAchat +
                ", dateAnnulation=" + dateAnnulation +
                ", dateRemboursement=" + dateRemboursement +
                ", evenement=" + (evenement != null ? evenement.getLibelle() : null) +
                ", utilisateur=" + (utilisateur != null ? utilisateur.getNom()+" "+utilisateur.getPrenom() : null) +
                '}';
    }

    public Ticket() {}

    public Ticket(Long id, String numeroPlace, String place, tools.StatutTicket statut, Integer prix, LocalDateTime dateAchat, LocalDateTime dateAnnulation, LocalDateTime dateRemboursement, Evenement evenement, Utilisateur utilisateur) {
        this.id = id;
        this.numeroPlace = numeroPlace;
        this.place = place;
        this.statut = statut;
        this.prix = prix;
        this.dateAchat = dateAchat;
        this.dateAnnulation = dateAnnulation;
        this.dateRemboursement = dateRemboursement;
        this.evenement = evenement;
        this.utilisateur = utilisateur;
    }

    // Ajout des accesseurs pour 'id' afin que Jackson puisse trouver la propriété référencée par @JsonIdentityInfo
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroPlace() {
        return numeroPlace;
    }

    public void setNumeroPlace(String numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public tools.StatutTicket getStatut() {
        return statut;
    }

    public void setStatut(tools.StatutTicket statut) {
        this.statut = statut;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public LocalDateTime getDateAchat() {
        return dateAchat;
    }

    @PrePersist
    public void onCreate() {
        this.dateAchat = LocalDateTime.now();
    }
    public void setDateAchat(LocalDateTime dateAchat) {
        this.dateAchat = dateAchat;
    }

    public LocalDateTime getDateAnnulation() {
        return dateAnnulation;
    }

    public void setDateAnnulation(LocalDateTime dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }

    public LocalDateTime getDateRemboursement() {
        return dateRemboursement;
    }

    public void setDateRemboursement(LocalDateTime dateRemboursement) {
        this.dateRemboursement = dateRemboursement;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Transient
    public boolean isRemboursable() {
        return this.dateAnnulation != null && this.dateRemboursement == null;
    }
}

