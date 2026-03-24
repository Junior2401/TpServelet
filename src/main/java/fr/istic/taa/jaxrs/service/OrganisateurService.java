package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.dao.generic.OrganisateurDao;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.domain.Organisateur;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class OrganisateurService {
    private final OrganisateurDao organisateurDao;

    public OrganisateurService(){
        organisateurDao = new OrganisateurDao();
    }

    // -------------------------
    // CREATE
    // -------------------------
    public Organisateur creerOrganisateur(String nom, String prenom, String email, String password,
                                          Adresse adresse, String societe, String telephonePro) {
        Organisateur organisateur = new Organisateur(null, nom, prenom, email, password, adresse, societe, telephonePro);
        organisateurDao.save(organisateur);
        return organisateur;
    }

    // -------------------------
    // READ ALL
    // -------------------------
    public List<Organisateur> getAll() {
        return organisateurDao.findAll();
    }

    // -------------------------
    // READ BY ID
    // -------------------------
    public Organisateur getById(Long id) {
        return organisateurDao.findOne(id);
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public Organisateur update(Long id, Organisateur updated) {
        Organisateur existing = getById(id);

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

        if (updated.getSociete() != null) {
            existing.setSociete(updated.getSociete());
        }

        if (updated.getTelephonePro() != null) {
            existing.setTelephonePro(updated.getTelephonePro());
        }

        return organisateurDao.update(existing);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete(Long id) {
        Organisateur organisateur = getById(id);
        if (organisateur != null) {
            organisateurDao.delete(organisateur);
        }
    }

    // -------------------------
    // MÉTHODES MÉTIER
    // -------------------------

    public List<Organisateur> getBySociete(String societe) {
        return organisateurDao.findBySociete(societe);
    }

    public Organisateur getUniqueBySociete(String societe) {
        return organisateurDao.findUniqueBySociete(societe);
    }

    public List<Organisateur> getByTelephonePro(String telephone) {
        return organisateurDao.findByTelephonePro(telephone);
    }

    public List<Organisateur> getOrganisteursWithEvenements() {
        return organisateurDao.findOrganisteursWithEvenements();
    }

    public List<Organisateur> getOrganisteursWithManyEvenements(int min) {
        return organisateurDao.findOrganisteursWithManyEvenements(min);
    }

    // -------------------------
    // STATISTIQUES
    // -------------------------

    /**
     * Nombre total d'organisateurs.
     */
    public Long getTotalOrganisateurs() {
        return (long) organisateurDao.findAll().size();
    }

    /**
     * Nombre d'organisateurs ayant organisé des événements.
     */
    public Long getNombreOrganisateursAvecEvenements() {
        return (long) getOrganisteursWithEvenements().size();
    }

    /**
     * Répartition des organisateurs par société.
     */
    public java.util.Map<String, Long> getRepartitionParSociete() {
        return organisateurDao.findAll().stream()
                .filter(o -> o.getSociete() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                        Organisateur::getSociete,
                        java.util.stream.Collectors.counting()
                ));
    }
}
