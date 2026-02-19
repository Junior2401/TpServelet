package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
public class TypeEvenement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    @Column(nullable = true)
    private String description;

    @OneToMany(mappedBy = "typeEvenement")
    private Collection<Evenement> evenements;

    public TypeEvenement() {}

    public TypeEvenement(Long id, String libelle, String description) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(Collection<Evenement> evenements) {
        this.evenements = evenements;
    }

    // -------------------------
    // MÉTHODES UTILITAIRES
    // -------------------------

    public void addEvenement(Evenement e) {
        evenements.add(e);
        e.setTypeEvenement(this);
    }

    public void removeEvenement(Evenement e) {
        evenements.remove(e);
        e.setTypeEvenement(null);
    }

    // -------------------------
    // equals / hashCode
    // -------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeEvenement)) return false;
        TypeEvenement that = (TypeEvenement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // -------------------------
    // toString (sans charger LAZY)
    // -------------------------

    @Override
    public String toString() {
        return "TypeEvenement{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}