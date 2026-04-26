package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.TypeEvenement;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;


public class TypeEvenementDao extends AbstractJpaDao<Long, TypeEvenement> {

    public TypeEvenementDao() {
        super();
        setClazz(TypeEvenement.class);
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Libellé
    // ---------------------------

    public TypeEvenement findByLibelle(String libelle) {
        try {
            return entityManager
                    .createQuery("SELECT t FROM TypeEvenement t WHERE LOWER(t.libelle) = LOWER(:libelle)", TypeEvenement.class)
                    .setParameter("libelle", libelle)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche Partielles
    // ---------------------------

    public List<TypeEvenement> findByLibelleContains(String libelle) {
        return entityManager
                .createQuery("SELECT t FROM TypeEvenement t WHERE LOWER(t.libelle) LIKE LOWER(:libelle) ORDER BY t.libelle ASC", TypeEvenement.class)
                .setParameter("libelle", "%" + libelle + "%")
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Types avec Description
    // ---------------------------

    public List<TypeEvenement> findWithDescription() {
        return entityManager
                .createQuery("SELECT t FROM TypeEvenement t WHERE t.description IS NOT NULL ORDER BY t.libelle ASC", TypeEvenement.class)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Types sans Description
    // ---------------------------

    public List<TypeEvenement> findWithoutDescription() {
        return entityManager
                .createQuery("SELECT t FROM TypeEvenement t WHERE t.description IS NULL ORDER BY t.libelle ASC", TypeEvenement.class)
                .getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche Avancée
    // ---------------------------

    public List<TypeEvenement> findTypesByLibelleStartingWith(String prefix) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TypeEvenement> cq = cb.createQuery(TypeEvenement.class);
        Root<TypeEvenement> root = cq.from(TypeEvenement.class);

        cq.select(root)
                .where(cb.like(cb.lower(root.get("libelle")), prefix.toLowerCase() + "%"))
                .orderBy(cb.asc(root.get("libelle")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // MÉTHODES UTILITAIRES
    // ---------------------------

    public Long findByIdTypeEvenement(int id) {
        List<TypeEvenement> result = entityManager
                .createQuery("SELECT t FROM TypeEvenement t WHERE t.id = :id", TypeEvenement.class)
                .setParameter("id", (long) id)
                .getResultList();
        return result.isEmpty() ? null : result.get(0).getId();
    }

}