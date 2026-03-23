package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Personne;
import fr.istic.taa.jaxrs.domain.Utilisateur;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class PersonneDao extends AbstractJpaDao<Long, Personne> {

    public PersonneDao() {
        setClazz(Personne.class);
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Nom
    // ---------------------------

    public List<Personne> findByNom(String nom) {
        return entityManager
                .createQuery("SELECT p FROM Personne p WHERE p.nom = :nom ORDER BY p.prenom ASC", Personne.class)
                .setParameter("nom", nom)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Email
    // ---------------------------

    public List<Personne> findByEmail(String email) {
        return entityManager
                .createQuery("SELECT p FROM Personne p WHERE p.email = :email", Personne.class)
                .setParameter("email", email)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Nom Complet
    // ---------------------------

    public List<Personne> findByNomComplet(String nom, String prenom) {
        return entityManager
                .createQuery("SELECT p FROM Personne p WHERE p.nom = :nom AND p.prenom = :prenom", Personne.class)
                .setParameter("nom", nom)
                .setParameter("prenom", prenom)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Prénom
    // ---------------------------

    public List<Personne> findByPrenom(String prenom) {
        return entityManager
                .createQuery("SELECT p FROM Personne p WHERE p.prenom = :prenom ORDER BY p.nom ASC", Personne.class)
                .setParameter("prenom", prenom)
                .getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche par Nom Préfixe
    // ---------------------------

    public List<Personne> findPersonnesByNomStartingWith(String prefix) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Personne> cq = cb.createQuery(Personne.class);
        Root<Personne> root = cq.from(Personne.class);

        cq.select(root)
                .where(cb.like(cb.lower(root.get("nom")), prefix.toLowerCase() + "%"))
                .orderBy(cb.asc(root.get("nom")), cb.asc(root.get("prenom")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche par Prénom Préfixe
    // ---------------------------

    public List<Personne> findPersonnesByPrenomStartingWith(String prefix) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Personne> cq = cb.createQuery(Personne.class);
        Root<Personne> root = cq.from(Personne.class);

        cq.select(root)
                .where(cb.like(cb.lower(root.get("prenom")), prefix.toLowerCase() + "%"))
                .orderBy(cb.asc(root.get("prenom")), cb.asc(root.get("nom")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Toutes les Personnes
    // ---------------------------

    public List<Personne> findAllPersonnes() {
        return entityManager
                .createQuery("SELECT p FROM Personne p ORDER BY p.nom ASC, p.prenom ASC", Personne.class)
                .getResultList();
    }

}