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
        return utilisateurDao.findAllUtilisateurs();
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
}
