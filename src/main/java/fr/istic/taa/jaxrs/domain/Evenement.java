package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fr.istic.taa.jaxrs.tools.tools;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "Représente un événement (concert, théâtre, etc.)")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)

@Entity
public class Evenement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de l'événement")
    private Long id;

    @Schema(description = "Libellé/nom de l'événement")
    private String libelle;

    @Schema(description = "Lieu de l'événement")
    private String lieu;

    @Schema(description = "Date et heure de l'événement")
    private LocalDateTime date;

    @Schema(description = "Capacité d'accueil")
    private Integer capacite;

    @Column(nullable = true)
    @Schema(description = "Description de l'événement")
    private String description;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Statut de l'événement (PLANIFIE, EN_COURS, TERMINE)")
    private tools.StatutEvenement statut;

    // --- TypeEvenement ↔ Evenement ---
    @ManyToOne
    @JoinColumn(name = "type_evenement_id")
    @Schema(description = "Type d'événement associé")
    private TypeEvenement typeEvenement;


    // --- Evenement ↔ Ticket ---
    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "Liste des tickets vendus pour cet événement")
    private Collection<Ticket> tickets;


    // --- Evenement ↔ Organisateur ---
    @ManyToMany
    @JoinTable(
            name = "organiser",
            joinColumns = @JoinColumn(name = "evenement_id"),
            inverseJoinColumns = @JoinColumn(name = "organisateur_id")
    )
    @Schema(description = "Liste des organisateurs de cet événement")
    private Set<Organisateur> organisateurs = new HashSet<>();


    // --- Evenement ↔ Artiste ---
    @ManyToMany
    @JoinTable(
            name = "participer",
            joinColumns = @JoinColumn(name = "evenement_id"),
            inverseJoinColumns = @JoinColumn(name = "artiste_id")
    )
    @Schema(description = "Liste des artistes participant à cet événement")
    private Set<Artiste> artistes = new HashSet<>();

    public Collection<Organisateur> getOrganisateurs() {
        return organisateurs;
    }

    public void setOrganisateurs(Set<Organisateur> organisateurs) {
        this.organisateurs = organisateurs;
    }

    public Collection<Artiste> getArtistes() {
        return artistes;
    }

    public void setArtistes(Set<Artiste> artistes) {
        this.artistes = artistes;
    }

    public Evenement() {}

    public Evenement(Long id, String libelle, String lieu, LocalDateTime date,
                     Integer capacite, String description, tools.StatutEvenement statut, TypeEvenement typeEvenement) {
        this.id = id;
        this.libelle = libelle;
        this.lieu = lieu;
        this.date = date;
        this.capacite = capacite;
        this.description = description;
        this.statut = statut;
        this.typeEvenement = typeEvenement;
    }

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public tools.StatutEvenement getStatut() {
        return statut;
    }

    public void setStatut(tools.StatutEvenement statut) {
        this.statut = statut;
    }

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }

    public TypeEvenement getTypeEvenement() {
        return typeEvenement;
    }

    public void setTypeEvenement(TypeEvenement typeEvenement) {
        this.typeEvenement = typeEvenement;
    }

    @Override
    public String toString() {
        String orgs = organisateurs.stream()
                .map(o -> o.getNom() + " " + o.getPrenom())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Aucun");

        String arts = artistes.stream()
                .map(a -> a.getNomDeScene() != null ? a.getNomDeScene() : a.getNom())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Aucun");

        return "Evenement{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", lieu='" + lieu + '\'' +
                ", date=" + date +
                ", capacite='" + capacite + '\'' +
                ", description='" + description + '\'' +
                ", statut=" + statut +
                ", organisateurs=[" + orgs + "]" +
                ", artistes=[" + arts + "]" +
                ", typeEvenement=" + (typeEvenement != null ? typeEvenement.getLibelle() : null) +
                '}';
    }
}