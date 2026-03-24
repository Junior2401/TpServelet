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

    // -------------------------
    // MÉTHODES MÉTIER
    // -------------------------

    public TypeEvenement getByLibelle(String libelle) {
        return typeEvenementDao.findByLibelle(libelle);
    }

    public List<TypeEvenement> getByLibelleContains(String libelle) {
        return typeEvenementDao.findByLibelleContains(libelle);
    }

    public List<TypeEvenement> getWithDescription() {
        return typeEvenementDao.findWithDescription();
    }

    public List<TypeEvenement> getWithoutDescription() {
        return typeEvenementDao.findWithoutDescription();
    }

    // -------------------------
    // STATISTIQUES
    // -------------------------

    /**
     * Nombre total de types d'événements.
     */
    public Long getTotalTypes() {
        return (long) typeEvenementDao.findAll().size();
    }

    /**
     * Nombre de types avec description.
     */
    public Long getNombreTypesAvecDescription() {
        return (long) getWithDescription().size();
    }

    /**
     * Pourcentage de types avec description.
     */
    public Double getPourcentageTypesAvecDescription() {
        long total = getTotalTypes();
        if (total == 0) return 0.0;
        return (double) getNombreTypesAvecDescription() / total * 100;
    }
}
