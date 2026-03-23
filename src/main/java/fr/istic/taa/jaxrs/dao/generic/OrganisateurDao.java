package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Organisateur;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class OrganisateurDao extends AbstractJpaDao<Long, Organisateur> {
    public OrganisateurDao() {
        setClazz(Organisateur.class);
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Société
    // ---------------------------

    public List<Organisateur> findBySociete(String societe) {
        return entityManager
                .createQuery("SELECT o FROM Organisateur o WHERE LOWER(o.societe) LIKE LOWER(:societe) ORDER BY o.societe ASC", Organisateur.class)
                .setParameter("societe", "%" + societe + "%")
                .getResultList();
    }

    public Organisateur findUniqueBySociete(String societe) {
        try {
            return entityManager
                    .createQuery("SELECT o FROM Organisateur o WHERE LOWER(o.societe) = LOWER(:societe)", Organisateur.class)
                    .setParameter("societe", societe)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Téléphone Professionnel
    // ---------------------------

    public List<Organisateur> findByTelephonePro(String telephone) {
        return entityManager
                .createQuery("SELECT o FROM Organisateur o WHERE o.telephonePro = :telephone", Organisateur.class)
                .setParameter("telephone", telephone)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Organisateurs avec Événements
    // ---------------------------

    public List<Organisateur> findOrganisteursWithEvenements() {
        return entityManager
                .createQuery("SELECT DISTINCT o FROM Organisateur o WHERE o.evenements IS NOT NULL AND SIZE(o.evenements) > 0 ORDER BY o.societe ASC", Organisateur.class)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Organisateurs Actifs
    // ---------------------------

    public List<Organisateur> findOrganisteursWithManyEvenements(int minEvenements) {
        return entityManager
                .createQuery("SELECT o FROM Organisateur o WHERE SIZE(o.evenements) >= :min ORDER BY SIZE(o.evenements) DESC", Organisateur.class)
                .setParameter("min", minEvenements)
                .getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche par Société Préfixe
    // ---------------------------

    public List<Organisateur> findOrganisteursBySocieteStartingWith(String prefix) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Organisateur> cq = cb.createQuery(Organisateur.class);
        Root<Organisateur> root = cq.from(Organisateur.class);

        cq.select(root)
                .where(cb.like(cb.lower(root.get("societe")), prefix.toLowerCase() + "%"))
                .orderBy(cb.asc(root.get("societe")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Tous les Organisateurs
    // ---------------------------

    public List<Organisateur> findAllOrganisateurs() {
        return entityManager
                .createQuery("SELECT o FROM Organisateur o ORDER BY o.societe ASC", Organisateur.class)
                .getResultList();
    }

}
