package fr.istic.taa.jaxrs;

import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.*;
import fr.istic.taa.jaxrs.tools.tools;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.*;
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
            // -----------------------------
            // 1. TYPES D'ÉVÉNEMENTS
            // -----------------------------
            TypeEvenement typeConcert = new TypeEvenement();
            typeConcert.setLibelle("Concert");
            typeConcert.setDescription("Événements musicaux live");
            manager.persist(typeConcert);

            TypeEvenement typeTheatre = new TypeEvenement();
            typeTheatre.setLibelle("Théâtre");
            typeTheatre.setDescription("Représentations théâtrales");
            manager.persist(typeTheatre);

            TypeEvenement typeSport = new TypeEvenement();
            typeSport.setLibelle("Sport");
            typeSport.setDescription("Compétitions sportives");
            manager.persist(typeSport);

            // -----------------------------
            // 2. ARTISTES
            // -----------------------------
            List<Artiste> artistes = createArtistes();
            artistes.forEach(manager::persist);

            // -----------------------------
            // 3. ORGANISATEURS
            // -----------------------------
            Organisateur org1 = createOrganisateur("Martin", "Jean", "Rock en Seine Corp");
            Organisateur org2 = createOrganisateur("Dupuis", "Claire", "Live Nation France");
            Organisateur org3 = createOrganisateur("Nguyen", "Paul", "EventMaster");
            manager.persist(org1);
            manager.persist(org2);
            manager.persist(org3);

            // -----------------------------
            // 4. ÉVÉNEMENTS
            // -----------------------------
            Evenement ev1 = new Evenement();
            ev1.setLibelle("Festival Spring 2026");
            ev1.setLieu("Parc des Expos, Rennes");
            ev1.setDate(LocalDateTime.now().plusMonths(2));
            ev1.setCapacite(5000);
            ev1.setDescription("Le plus grand festival de printemps.");
            ev1.setStatut(tools.StatutEvenement.PLANIFIE);
            ev1.setTypeEvenement(typeConcert);
            ev1.setTypesPlace(List.of("VIP", "STANDARD", "TRIBUNE"));
            ev1.setArtistes(new HashSet<>(artistes.subList(0, 2)));
            ev1.getOrganisateurs().add(org1);
            manager.persist(ev1);

            Evenement ev2 = new Evenement();
            ev2.setLibelle("Match Rennes vs Lyon");
            ev2.setLieu("Roazhon Park, Rennes");
            ev2.setDate(LocalDateTime.now().plusDays(15));
            ev2.setCapacite(29000);
            ev2.setDescription("Match de Ligue 1");
            ev2.setStatut(tools.StatutEvenement.VALIDE);
            ev2.setTypeEvenement(typeSport);
            ev2.setTypesPlace(List.of("PELOUSE", "TRIBUNE NORD", "TRIBUNE SUD"));
            ev2.getOrganisateurs().add(org3);
            manager.persist(ev2);

            Evenement ev3 = new Evenement();
            ev3.setLibelle("Pièce : Le Malade Imaginaire");
            ev3.setLieu("Théâtre National de Bretagne");
            ev3.setDate(LocalDateTime.now().plusWeeks(3));
            ev3.setCapacite(800);
            ev3.setDescription("Représentation classique de Molière.");
            ev3.setStatut(tools.StatutEvenement.PLANIFIE);
            ev3.setTypeEvenement(typeTheatre);
            ev3.setTypesPlace(List.of("ORCHESTRE", "BALCON", "LOGE"));
            ev3.getOrganisateurs().add(org2);
            manager.persist(ev3);

            // -----------------------------
            // 5. UTILISATEUR + TICKETS
            // -----------------------------
            Utilisateur client = createUtilisateur();
            manager.persist(client);

            manager.persist(createTicket("A-12", "Rangée 1", 55, ev1, client));
            manager.persist(createTicket("A-13", "Rangée 1", 55, ev1, client));
            manager.persist(createTicket("B-05", "Tribune Nord", 35, ev2, client));

            // -----------------------------
            // 6. ADMINISTRATEUR
            // -----------------------------
            manager.persist(createAdministrateur());

            tx.commit();
            logger.info("Données initialisées avec succès !");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logger.severe("Erreur lors de l'initialisation : " + e.getMessage());
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    // -----------------------------------------------------
    // MÉTHODES DE CRÉATION
    // -----------------------------------------------------

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

        Artiste a3 = new Artiste();
        a3.setNom("Khan");
        a3.setPrenom("Amina");
        a3.setNomDeScene("Amina K.");
        a3.setStyleArtistique("Soul");
        a3.setEmail("amina@soul.com");
        list.add(a3);

        return list;
    }

    private Organisateur createOrganisateur(String nom, String prenom, String societe) {
        Organisateur orga = new Organisateur();
        orga.setNom(nom);
        orga.setPrenom(prenom);
        orga.setEmail(prenom.toLowerCase() + "." + nom.toLowerCase() + "@example.com");
        orga.setSociete(societe);
        orga.setTelephonePro("0123456789");
        orga.setAdresse(new Adresse(10, "Rue Centrale", "Rennes (35000)"));
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
