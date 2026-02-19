package fr.istic.taa.jaxrs.service;


import fr.istic.taa.jaxrs.dao.generic.PersonneDao;
import fr.istic.taa.jaxrs.domain.Personne;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class PersonneService {
    private final PersonneDao personneDao;

    public PersonneService() {
        personneDao = new PersonneDao();
    }

    // -------------------------
    // READ ALL
    // -------------------------
    public List<Personne> getAll() {
        return personneDao.findAllPersonnes();
    }

    // -------------------------
    // READ BY ID
    // -------------------------
    public Personne getById(Long id) {
        return personneDao.findOne(id);
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public Personne update(Long id, Personne updated) {
        Personne existing = getById(id);

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

        return personneDao.update(existing);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete(Long id) {
        Personne personne = getById(id);
        if (personne != null) {
            personneDao.delete(personne);
        }
    }
}
