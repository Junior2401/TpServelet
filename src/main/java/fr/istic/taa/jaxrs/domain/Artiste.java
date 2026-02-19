package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@DiscriminatorValue("ARTISTE")
public class Artiste extends Personne {

    private String nomDeScene;
    private String styleArtistique;

    @ManyToMany(mappedBy = "artistes")

    private Set<Evenement> evenements = new HashSet<>();

    public Collection<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(Set<Evenement> evenements) {
        this.evenements = evenements;
    }

    public Artiste() {
        super();
    }

    public Artiste(Long id, String nom, String prenom, String email, String password,
                   Adresse adresse, String nomDeScene, String styleArtistique) {
        super(id, nom, prenom, email, password, adresse);
        this.nomDeScene = nomDeScene;
        this.styleArtistique = styleArtistique;
    }

    public String getNomDeScene() {
        return nomDeScene;
    }

    public void setNomDeScene(String nomDeScene) {
        this.nomDeScene = nomDeScene;
    }

    public String getStyleArtistique() {
        return styleArtistique;
    }

    public void setStyleArtistique(String styleArtistique) {
        this.styleArtistique = styleArtistique;
    }

    @Override
    public String toString() {
        return "Artiste{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", nomDeScene='" + nomDeScene + '\'' +
                ", styleArtistique='" + styleArtistique + '\'' +
                '}';
    }
}