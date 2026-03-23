package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.tools.tools;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.List;

public class EvenementDao extends AbstractJpaDao<Long, Evenement> {

    public EvenementDao() {
        setClazz(Evenement.class);
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Statut
    // ---------------------------

    public List<Evenement> findByStatut(tools.StatutEvenement statut) {
        return entityManager
                .createQuery("SELECT e FROM Evenement e WHERE e.statut = :statut ORDER BY e.date ASC", Evenement.class)
                .setParameter("statut", statut)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Libellé
    // ---------------------------

    public List<Evenement> findByLibelle(String libelle) {
        return entityManager
                .createQuery("SELECT e FROM Evenement e WHERE LOWER(e.libelle) LIKE LOWER(:libelle) ORDER BY e.date ASC", Evenement.class)
                .setParameter("libelle", "%" + libelle + "%")
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Lieu
    // ---------------------------

    public List<Evenement> findByLieu(String lieu) {
        return entityManager
                .createQuery("SELECT e FROM Evenement e WHERE LOWER(e.lieu) LIKE LOWER(:lieu) ORDER BY e.date ASC", Evenement.class)
                .setParameter("lieu", "%" + lieu + "%")
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Période
    // ---------------------------

    public List<Evenement> findByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return entityManager
                .createQuery("SELECT e FROM Evenement e WHERE e.date BETWEEN :dateDebut AND :dateFin ORDER BY e.date ASC", Evenement.class)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Événements à Venir
    // ---------------------------

    public List<Evenement> findUpcoming(LocalDateTime now) {
        return entityManager
                .createQuery("SELECT e FROM Evenement e WHERE e.date >= :now AND e.statut IN ('PLANIFIE', 'EN_COURS') ORDER BY e.date ASC", Evenement.class)
                .setParameter("now", now)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Événements Passés
    // ---------------------------

    public List<Evenement> findPast(LocalDateTime now) {
        return entityManager
                .createQuery("SELECT e FROM Evenement e WHERE e.date < :now AND e.statut = 'TERMINE' ORDER BY e.date DESC", Evenement.class)
                .setParameter("now", now)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Événements par Type
    // ---------------------------

    public List<Evenement> findByTypeEvenementId(Long typeId) {
        return entityManager
                .createQuery("SELECT e FROM Evenement e WHERE e.typeEvenement.id = :typeId ORDER BY e.date ASC", Evenement.class)
                .setParameter("typeId", typeId)
                .getResultList();
    }

    public Long findByIdEvenement(int id) {
        List<Evenement> result = entityManager
                .createQuery("SELECT e FROM Evenement e WHERE e.id = :id", Evenement.class)
                .setParameter("id", (long) id)
                .getResultList();
        return result.isEmpty() ? null : result.get(0).getId();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Événements par Capacité
    // ---------------------------

    public List<Evenement> findByCapaciteMin(Integer minCapacite) {
        return entityManager
                .createQuery("SELECT e FROM Evenement e WHERE e.capacite >= :minCapacite ORDER BY e.capacite DESC", Evenement.class)
                .setParameter("minCapacite", minCapacite)
                .getResultList();
    }

    public List<Evenement> findByCapaciteRange(Integer minCapacite, Integer maxCapacite) {
        return entityManager
                .createQuery("SELECT e FROM Evenement e WHERE e.capacite BETWEEN :min AND :max ORDER BY e.capacite ASC", Evenement.class)
                .setParameter("min", minCapacite)
                .setParameter("max", maxCapacite)
                .getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche Avancée par Libellé
    // ---------------------------

    public List<Evenement> findEvenementsByLibelleStartingWith(String prefix) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Evenement> cq = cb.createQuery(Evenement.class);
        Root<Evenement> root = cq.from(Evenement.class);

        cq.select(root)
                .where(cb.like(cb.lower(root.get("libelle")), prefix.toLowerCase() + "%"))
                .orderBy(cb.asc(root.get("date")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche par Statut et Période
    // ---------------------------

    public List<Evenement> findByStatutAndDate(tools.StatutEvenement statut, LocalDateTime dateMin) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Evenement> cq = cb.createQuery(Evenement.class);
        Root<Evenement> root = cq.from(Evenement.class);

        cq.select(root)
                .where(cb.and(
                        cb.equal(root.get("statut"), statut),
                        cb.greaterThanOrEqualTo(root.get("date"), dateMin)
                ))
                .orderBy(cb.asc(root.get("date")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Tous les Événements Ordonnés
    // ---------------------------

    public List<Evenement> findAllEvenements() {
        return entityManager
                .createQuery("SELECT e FROM Evenement e ORDER BY e.date ASC", Evenement.class)
                .getResultList();
    }

}