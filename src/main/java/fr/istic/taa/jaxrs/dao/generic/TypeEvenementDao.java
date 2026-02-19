package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.TypeEvenement;
import jakarta.persistence.NoResultException;

import java.util.List;


public class TypeEvenementDao extends AbstractJpaDao<Long, TypeEvenement> {

    public TypeEvenementDao() {
        super();
        setClazz(TypeEvenement.class);
    }

    // -------------------------
    // FIND ALL
    // -------------------------
    public List<TypeEvenement> findAllTypeEvenements() {
        return entityManager
                .createQuery("SELECT t FROM TypeEvenement t", TypeEvenement.class)
                .getResultList();
    }

    // -------------------------
    // FIND BY ID (safe)
    // -------------------------
    public TypeEvenement findByIdTypeEvenement(int id) {
        try {
            return entityManager
                    .createQuery(
                            "SELECT t FROM TypeEvenement t WHERE t.id = :id",
                            TypeEvenement.class
                    )
                    .setParameter("id", (long) id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // évite une exception 500 côté REST
        }
    }

    // -------------------------
    // DELETE BY ID
    // -------------------------
    public boolean deleteById(long id) {
        TypeEvenement t = entityManager.find(TypeEvenement.class, id);
        if (t == null) return false;

        entityManager.getTransaction().begin();
        entityManager.remove(t);
        entityManager.getTransaction().commit();

        return true;
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public TypeEvenement update(TypeEvenement t) {
        entityManager.getTransaction().begin();
        TypeEvenement merged = entityManager.merge(t);
        entityManager.getTransaction().commit();
        return merged;
    }

}