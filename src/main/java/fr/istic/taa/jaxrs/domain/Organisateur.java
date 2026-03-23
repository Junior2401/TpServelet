package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;


@Schema(description = "Représente un organisateur d'événements")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@DiscriminatorValue("ORGANISATEUR")
public class Organisateur extends Personne {

    @Schema(description = "Nom de la société de l'organisateur")
    private String societe;

    @Schema(description = "Téléphone professionnel de l'organisateur")
    private String telephonePro;

    @ManyToMany(mappedBy = "organisateurs")
    @Schema(description = "Liste des événements organisés")
    private Set<Evenement> evenements = new HashSet<>();

    public Set<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(Set<Evenement> evenements) {
        this.evenements = evenements;
    }

    public Organisateur() {
        super();
    }

    public Organisateur(Long id, String nom, String prenom, String email, String password, Adresse  adresse,
                        String societe, String telephonePro) {
        super(id, nom, prenom, email, password, adresse);
        this.societe = societe;
        this.telephonePro = telephonePro;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public String getTelephonePro() {
        return telephonePro;
    }

    public void setTelephonePro(String telephonePro) {
        this.telephonePro = telephonePro;
    }

    @Override
    public String toString() {
        return "Organisateur{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", societe='" + societe + '\'' +
                ", telephonePro='" + telephonePro + '\'' +
                '}';
    }
}