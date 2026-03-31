package fr.istic.taa.jaxrs;

import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.*;
import fr.istic.taa.jaxrs.tools.tools;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class DataInitializer {
    private static final Logger logger = Logger.getLogger(DataInitializer.class.getName());

    private DataInitializer() {}

    public static void initialize() {
        new DataInitializer()._initialize();
    }

    private void _initialize() {
        EntityManager manager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            // 1. Création des Types d'Événements
            TypeEvenement typeConcert = new TypeEvenement();
            typeConcert.setLibelle("Concert");
            typeConcert.setDescription("Événements musicaux live");
            manager.persist(typeConcert);

            // 2. Création des Artistes
            List<Artiste> artistes = createArtistes();
            artistes.forEach(manager::persist);

            // 3. Création des Organisateurs
            Organisateur rockEnSeine = createOrganisateur();
            manager.persist(rockEnSeine);

            // 4. Création de l'Événement
            Evenement evenement = new Evenement();
            evenement.setLibelle("Festival Spring 2026");
            evenement.setLieu("Parc des Expos, Rennes");
            evenement.setDate(LocalDateTime.now().plusMonths(2));
            evenement.setCapacite(5000);
            evenement.setDescription("Le plus grand festival de printemps.");
            evenement.setStatut(tools.StatutEvenement.PLANIFIE);
            evenement.setTypeEvenement(typeConcert);

            // Associations
            evenement.setArtistes(new HashSet<>(artistes.subList(0, 2))); // Prend les 2 premiers
            evenement.getOrganisateurs().add(rockEnSeine);

            manager.persist(evenement);

            // 5. Création Utilisateur & Tickets
            Utilisateur client = createUtilisateur();
            manager.persist(client);

            Ticket t1 = createTicket("A-12", "Rangée 1", 55, evenement, client);
            Ticket t2 = createTicket("A-13", "Rangée 1", 55, evenement, client);

            manager.persist(t1);
            manager.persist(t2);

            // 6. Création Administrateur
            manager.persist(createAdministrateur());

            tx.commit();
            logger.info("Données initialisées avec succès !");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            logger.severe("Erreur lors de l'initialisation : " + e.getMessage());
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    private List<Artiste> createArtistes() {
        List<Artiste> list = new ArrayList<>();

        Artiste a1 = new Artiste();
        a1.setNom("Durand");
        a1.setPrenom("Lucas");
        a1.setNomDeScene("Stellar");
        a1.setStyleArtistique("Electro");
        a1.setEmail("stellar@music.com");
        a1.setAdresse(new Adresse(2, " rue des Arts", "Paris (75000)"));
        list.add(a1);

        Artiste a2 = new Artiste();
        a2.setNom("Moreau");
        a2.setPrenom("Emma");
        a2.setNomDeScene("Luna Vox");
        a2.setStyleArtistique("Pop");
        a2.setEmail("luna@music.com");
        list.add(a2);

        return list;
    }

    private Organisateur createOrganisateur() {
        Organisateur orga = new Organisateur();
        orga.setNom("Martin");
        orga.setPrenom("Jean");
        orga.setEmail("contact@rockenseine.fr");
        orga.setSociete("Rock en Seine Corp");
        orga.setTelephonePro("0123456789");
        orga.setAdresse(new Adresse(5, "Avenue de la République", "Saint-Cloud (92210)"));
        return orga;
    }

    private Utilisateur createUtilisateur() {
        Utilisateur user = new Utilisateur();
        user.setNom("Dupond");
        user.setPrenom("Michel");
        user.setEmail("michel.dupond@gmail.com");
        user.setTelephone("0607080910");
        user.setAdresse(new Adresse(1, "rue de la Paix", "Rennes (35000)"));
        return user;
    }

    private Administrateur createAdministrateur() {
        Administrateur admin = new Administrateur();
        admin.setNom("Admin");
        admin.setPrenom("System");
        admin.setEmail("admin@ticket-system.com");
        admin.setRole("SUPER_USER");
        return admin;
    }

    private Ticket createTicket(String num, String place, Integer prix, Evenement ev, Utilisateur u) {
        Ticket t = new Ticket();
        t.setNumeroPlace(num);
        t.setPlace(place);
        t.setPrix(prix);
        t.setStatut(tools.StatutTicket.ACHETE);
        t.setDateAchat(LocalDateTime.now());
        t.setEvenement(ev);
        t.setUtilisateur(u);
        return t;
    }
}