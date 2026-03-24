package fr.istic.taa.jaxrs.service;


import fr.istic.taa.jaxrs.dao.generic.AdministrateurDao;
import fr.istic.taa.jaxrs.domain.Administrateur;
import fr.istic.taa.jaxrs.domain.Adresse;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class AdministrateurService {

    private final AdministrateurDao administrateurDao;

    public AdministrateurService() {
        this.administrateurDao = new AdministrateurDao();
    }

    // -------------------------
    // CREATE
    // -------------------------
    public Administrateur creerAdministrateur(String nom, String prenom, String email, String password, Adresse adresse, String role) {
        Administrateur admin = new Administrateur(null, nom, prenom, email, password, adresse, role);
        administrateurDao.save(admin);
        return admin;
    }

    // -------------------------
    // READ ALL
    // -------------------------
    public List<Administrateur> getAll() {
        return administrateurDao.findAll();
    }

    // -------------------------
    // READ BY ID
    // -------------------------
    public Administrateur getById(Long id) {
        return administrateurDao.findOne(id);
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public Administrateur update(Long id, Administrateur updated) {
        Administrateur existing = getById(id);

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

        if (updated.getRole() != null) {
            existing.setRole(updated.getRole());
        }

        return administrateurDao.update(existing);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete(Long id) {
        Administrateur admin = getById(id);
        if (admin != null) {
            administrateurDao.delete(admin);
        }
    }

    // -------------------------
    // MÉTHODES MÉTIER
    // -------------------------

    public List<Administrateur> getByRole(String role) {
        return administrateurDao.findByRole(role);
    }

    public List<Administrateur> getByEmail(String email) {
        return administrateurDao.findByEmail(email);
    }

    public Administrateur getUniqueByEmail(String email) {
        return administrateurDao.findUniqueByEmail(email);
    }

    public List<Administrateur> getByNomComplet(String nom, String prenom) {
        return administrateurDao.findByNomComplet(nom, prenom);
    }

    // -------------------------
    // STATISTIQUES
    // -------------------------

    /**
     * Nombre total d'administrateurs.
     */
    public Long getTotalAdministrateurs() {
        return (long) administrateurDao.findAll().size();
    }

    /**
     * Répartition des administrateurs par rôle.
     */
    public java.util.Map<String, Long> getRepartitionParRole() {
        return administrateurDao.findAll().stream()
                .filter(a -> a.getRole() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                        Administrateur::getRole,
                        java.util.stream.Collectors.counting()
                ));
    }
}