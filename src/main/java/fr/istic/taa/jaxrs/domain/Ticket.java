package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fr.istic.taa.jaxrs.tools.tools;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroPlace;

    private String place;

    @Enumerated(EnumType.STRING)
    private tools.StatutTicket statut;

    private Integer prix;

    private LocalDateTime dateAchat;

    @Column(nullable = true)
    private LocalDateTime dateAnnulation;

    @Column(nullable = true)
    private LocalDateTime dateRemboursement;

    @ManyToOne
    @JoinColumn(name = "evenement_id")
    private Evenement evenement;

    @ManyToOne
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

}

