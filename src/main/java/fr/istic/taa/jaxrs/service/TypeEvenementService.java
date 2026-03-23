package fr.istic.taa.jaxrs.service;


import fr.istic.taa.jaxrs.dao.generic.TypeEvenementDao;
import fr.istic.taa.jaxrs.domain.TypeEvenement;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class TypeEvenementService {
    private final TypeEvenementDao typeEvenementDao;

    public TypeEvenementService() {
        this.typeEvenementDao = new TypeEvenementDao();
    }

    // -------------------------
    // CREATE
    // -------------------------
    public TypeEvenement creerTypeEvenement(String libelle, String description) {
        TypeEvenement typeEvenement = new TypeEvenement(null, libelle, description);
        typeEvenementDao.save(typeEvenement);
        return typeEvenement;
    }

    // -------------------------
    // READ ALL
    // -------------------------
    public List<TypeEvenement> getAll() {
        return typeEvenementDao.findAll();
    }

    // -------------------------
    // READ BY ID
    // -------------------------
    public TypeEvenement getById(Long id) {
        return typeEvenementDao.findOne(id);
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public TypeEvenement update(Long id, TypeEvenement updated) {
        TypeEvenement existing = getById(id);

        if (existing == null) {
            return null; // REST renverra 404
        }

        if (updated.getLibelle() != null) {
            existing.setLibelle(updated.getLibelle());
        }

        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }
        return typeEvenementDao.update(existing);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete(TypeEvenement typeEvenement) {
        typeEvenementDao.delete(typeEvenement);
    }



}

