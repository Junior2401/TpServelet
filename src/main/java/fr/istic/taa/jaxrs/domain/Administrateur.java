package fr.istic.taa.jaxrs.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Schema(description = "Représente un administrateur du système")
@Entity
@DiscriminatorValue("ADMIN")
public class Administrateur extends Personne{
    @Schema(description = "Rôle de l'administrateur")
    private String role;

    public Administrateur() {
        super();
    }

    public Administrateur(Long id, String nom, String prenom, String email, String password, Adresse adresse, String role) {
        super(id, nom, prenom, email, password, adresse);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


}
