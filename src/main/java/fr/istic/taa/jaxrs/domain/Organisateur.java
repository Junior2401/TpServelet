package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;


@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@DiscriminatorValue("ORGANISATEUR")
public class Organisateur extends Personne {

    private String societe;
    private String telephonePro;

    @ManyToMany(mappedBy = "organisateurs")
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