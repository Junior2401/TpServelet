package fr.istic.taa.jaxrs.service;


import fr.istic.taa.jaxrs.dao.generic.UtilisateurDao;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.domain.Utilisateur;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class UtilisateurService {
    private final UtilisateurDao utilisateurDao;

    public UtilisateurService(){
        utilisateurDao = new UtilisateurDao();
    }

    // -------------------------
    // CREATE
    // -------------------------
    public Utilisateur creerUtilisateur(String nom, String prenom, String email, String password, Adresse adresse, String telephone) {
        Utilisateur user = new Utilisateur(null, nom, prenom, email, password, adresse, telephone);
        utilisateurDao.save(user);
        return user;
    }

    // -------------------------
    // READ ALL
    // -------------------------
    public List<Utilisateur> getAll() {
        return utilisateurDao.findAll();
    }

    // -------------------------
    // READ BY ID
    // -------------------------
    public Utilisateur getById(Long id) {
        return utilisateurDao.findOne(id);
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public Utilisateur update(Long id, Utilisateur updated) {
        Utilisateur existing = getById(id);

        if (existing == null) {
            return null; // REST renverra 404
        }

        if (updated.getNom() != null) {
            existing.setNom(updated.getNom());
        }

        if (updated.getPrenom() != null) {
            existing.setPrenom(updated.getPrenom());
        }

        if (updated.getEmail() != null) {
            existing.setEmail(updated.getEmail());
        }

        if (updated.getPassword() != null) {
            existing.setPassword(updated.getPassword());
        }

        if (updated.getTelephone() != null) {
            existing.setTelephone(updated.getTelephone());
        }

        return utilisateurDao.update(existing);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete(Long id) {
        Utilisateur utilisateur = getById(id);
        if (utilisateur != null) {
            utilisateurDao.delete(utilisateur);
        }
    }

    // -------------------------
    // MÉTHODES MÉTIER
    // -------------------------

    public List<Utilisateur> getByNom(String nom) {
        return utilisateurDao.findByNom(nom);
    }

    public List<Utilisateur> getByEmail(String email) {
        return utilisateurDao.findByEmail(email);
    }

    public Utilisateur getUniqueByEmail(String email) {
        return utilisateurDao.findUniqueByEmail(email);
    }

    public List<Utilisateur> getByNomComplet(String nom, String prenom) {
        return utilisateurDao.findByNomComplet(nom, prenom);
    }

    public List<Utilisateur> getByPrenom(String prenom) {
        return utilisateurDao.findByPrenom(prenom);
    }

    public List<Utilisateur> getByTelephone(String telephone) {
        return utilisateurDao.findByTelephone(telephone);
    }

    public List<Utilisateur> getUsersWithTickets() {
        return utilisateurDao.findUsersWithTickets();
    }

    // -------------------------
    // STATISTIQUES
    // -------------------------

    /**
     * Nombre total d'utilisateurs.
     */
    public Long getTotalUtilisateurs() {
        return (long) utilisateurDao.findAll().size();
    }

    /**
     * Nombre d'utilisateurs ayant acheté des tickets.
     */
    public Long getNombreUtilisateursAvecTickets() {
        return (long) getUsersWithTickets().size();
    }

    /**
     * Pourcentage d'utilisateurs actifs (ayant des tickets).
     */
    public Double getPourcentageUtilisateursActifs() {
        long total = getTotalUtilisateurs();
        if (total == 0) return 0.0;
        return (double) getNombreUtilisateursAvecTickets() / total * 100;
    }

    public java.util.Map<String, Long> getRepartitionParVille() {
        return utilisateurDao.findAll().stream()
                .filter(u -> u.getAdresse() != null && u.getAdresse().getVille() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                        u -> u.getAdresse().getVille(),
                        java.util.stream.Collectors.counting()
                ));
    }
}
