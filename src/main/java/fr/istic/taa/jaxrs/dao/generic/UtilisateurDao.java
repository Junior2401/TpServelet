package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class UtilisateurDao extends AbstractJpaDao<Long, Utilisateur> {

    public UtilisateurDao() {
        setClazz(Utilisateur.class);
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Nom
    // ---------------------------

    public List<Utilisateur> findByNom(String nom) {
        return entityManager
                .createQuery("SELECT u FROM Utilisateur u WHERE u.nom = :nom", Utilisateur.class)
                .setParameter("nom", nom)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Email
    // ---------------------------

    public List<Utilisateur> findByEmail(String email) {
        return entityManager
                .createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                .setParameter("email", email)
                .getResultList();
    }

    public Utilisateur findUniqueByEmail(String email) {
        try {
            return entityManager
                    .createQuery("SELECT u FROM Utilisateur u WHERE LOWER(u.email) = LOWER(:email)", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Nom Complet
    // ---------------------------

    public List<Utilisateur> findByNomComplet(String nom, String prenom) {
        return entityManager
                .createQuery("SELECT u FROM Utilisateur u WHERE u.nom = :nom AND u.prenom = :prenom", Utilisateur.class)
                .setParameter("nom", nom)
                .setParameter("prenom", prenom)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Prénom
    // ---------------------------

    public List<Utilisateur> findByPrenom(String prenom) {
        return entityManager
                .createQuery("SELECT u FROM Utilisateur u WHERE u.prenom = :prenom ORDER BY u.nom ASC", Utilisateur.class)
                .setParameter("prenom", prenom)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Téléphone
    // ---------------------------

    public List<Utilisateur> findByTelephone(String telephone) {
        return entityManager
                .createQuery("SELECT u FROM Utilisateur u WHERE u.telephone = :telephone", Utilisateur.class)
                .setParameter("telephone", telephone)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Utilisateurs avec Tickets
    // ---------------------------

    public List<Utilisateur> findUsersWithTickets() {
        return entityManager
                .createQuery("SELECT DISTINCT u FROM Utilisateur u WHERE u.tickets IS NOT NULL AND SIZE(u.tickets) > 0 ORDER BY u.nom ASC", Utilisateur.class)
                .getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche par Préfixe
    // ---------------------------

    public List<Utilisateur> findUsersWithNameStartingWith(String prefix) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Utilisateur> cq = cb.createQuery(Utilisateur.class);
        Root<Utilisateur> root = cq.from(Utilisateur.class);

        cq.select(root)
                .where(cb.like(cb.lower(root.get("nom")), prefix.toLowerCase() + "%"))
                .orderBy(cb.asc(root.get("nom")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche par Prénom Préfixe
    // ---------------------------

    public List<Utilisateur> findUsersWithPrenomStartingWith(String prefix) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Utilisateur> cq = cb.createQuery(Utilisateur.class);
        Root<Utilisateur> root = cq.from(Utilisateur.class);

        cq.select(root)
                .where(cb.like(cb.lower(root.get("prenom")), prefix.toLowerCase() + "%"))
                .orderBy(cb.asc(root.get("prenom")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Tous les Utilisateurs Ordonnés
    // ---------------------------

    public List<Utilisateur> findAllUtilisateurs() {
        return entityManager
                .createQuery("SELECT u FROM Utilisateur u ORDER BY u.nom ASC, u.prenom ASC", Utilisateur.class)
                .getResultList();
    }

}