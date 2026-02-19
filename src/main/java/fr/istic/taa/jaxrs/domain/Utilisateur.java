package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@DiscriminatorValue("UTILISATEUR")
public class Utilisateur extends Personne {
    private String telephone;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Ticket> tickets;

    public Utilisateur() {
        super();
    }

    public Utilisateur(Long id, String nom, String prenom, String email, String password, Adresse adresse, String telephone) {
        super(id, nom, prenom, email, password, adresse);
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }


}
