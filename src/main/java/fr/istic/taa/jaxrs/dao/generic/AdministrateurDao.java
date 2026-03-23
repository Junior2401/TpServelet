package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Administrateur;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class AdministrateurDao extends AbstractJpaDao<Long, Administrateur> {
    public AdministrateurDao() {
        setClazz(Administrateur.class);
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Rôle
    // ---------------------------

    public List<Administrateur> findByRole(String role) {
        return entityManager
                .createQuery("SELECT a FROM Administrateur a WHERE a.role = :role ORDER BY a.nom ASC", Administrateur.class)
                .setParameter("role", role)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Email
    // ---------------------------

    public List<Administrateur> findByEmail(String email) {
        return entityManager
                .createQuery("SELECT a FROM Administrateur a WHERE a.email = :email", Administrateur.class)
                .setParameter("email", email)
                .getResultList();
    }

    public Administrateur findUniqueByEmail(String email) {
        try {
            return entityManager
                    .createQuery("SELECT a FROM Administrateur a WHERE LOWER(a.email) = LOWER(:email)", Administrateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Nom Complet
    // ---------------------------

    public List<Administrateur> findByNomComplet(String nom, String prenom) {
        return entityManager
                .createQuery("SELECT a FROM Administrateur a WHERE a.nom = :nom AND a.prenom = :prenom", Administrateur.class)
                .setParameter("nom", nom)
                .setParameter("prenom", prenom)
                .getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche par Nom Préfixe
    // ---------------------------

    public List<Administrateur> findAdminsByNomStartingWith(String prefix) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Administrateur> cq = cb.createQuery(Administrateur.class);
        Root<Administrateur> root = cq.from(Administrateur.class);

        cq.select(root)
                .where(cb.like(cb.lower(root.get("nom")), prefix.toLowerCase() + "%"))
                .orderBy(cb.asc(root.get("nom")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche par Rôle
    // ---------------------------

    public List<Administrateur> findAdminsByRole(String role) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Administrateur> cq = cb.createQuery(Administrateur.class);
        Root<Administrateur> root = cq.from(Administrateur.class);

        cq.select(root)
                .where(cb.like(cb.lower(root.get("role")), role.toLowerCase() + "%"))
                .orderBy(cb.asc(root.get("nom")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Tous les Administrateurs
    // ---------------------------

    public List<Administrateur> findAllAdministrateurs() {
        return entityManager
                .createQuery("SELECT a FROM Administrateur a ORDER BY a.nom ASC, a.prenom ASC", Administrateur.class)
                .getResultList();
    }

}
