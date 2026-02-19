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
        return administrateurDao.findAllAdmins();
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
}