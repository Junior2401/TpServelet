package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.tools.tools;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.List;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {

    public TicketDao() {
        setClazz(Ticket.class);
    }

    // ---------------------------
    // MÉTHODES MÉTIER - NamedQueries
    // ---------------------------

    public List<Ticket> findByUtilisateur(Long userId) {
        return entityManager
                .createNamedQuery("Ticket.findByUtilisateur", Ticket.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Ticket> findByEvenement(Long eventId) {
        return entityManager
                .createNamedQuery("Ticket.findByEvenement", Ticket.class)
                .setParameter("eventId", eventId)
                .getResultList();
    }

    public List<Ticket> findByStatut(tools.StatutTicket statut) {
        return entityManager
                .createNamedQuery("Ticket.findByStatut", Ticket.class)
                .setParameter("statut", statut)
                .getResultList();
    }

    public List<Ticket> findPurchasedAfter(LocalDateTime dateMin) {
        return entityManager
                .createNamedQuery("Ticket.findPurchasedAfter", Ticket.class)
                .setParameter("dateMin", dateMin)
                .getResultList();
    }

    public List<Ticket> findCancelledNotRefunded() {
        return entityManager
                .createNamedQuery("Ticket.findCancelledNotRefunded", Ticket.class)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Numéro de Place
    // ---------------------------

    public List<Ticket> findByNumeroPlace(String numeroPlace) {
        return entityManager
                .createQuery("SELECT t FROM Ticket t WHERE t.numeroPlace = :numeroPlace", Ticket.class)
                .setParameter("numeroPlace", numeroPlace)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Place
    // ---------------------------

    public List<Ticket> findByPlace(String place) {
        return entityManager
                .createQuery("SELECT t FROM Ticket t WHERE LOWER(t.place) LIKE LOWER(:place) ORDER BY t.numeroPlace ASC", Ticket.class)
                .setParameter("place", "%" + place + "%")
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Prix
    // ---------------------------

    public List<Ticket> findByPrixRange(Integer minPrix, Integer maxPrix) {
        return entityManager
                .createQuery("SELECT t FROM Ticket t WHERE t.prix BETWEEN :min AND :max ORDER BY t.prix ASC", Ticket.class)
                .setParameter("min", minPrix)
                .setParameter("max", maxPrix)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Tickets Remboursés
    // ---------------------------

    public List<Ticket> findRefundedTickets() {
        return entityManager
                .createQuery("SELECT t FROM Ticket t WHERE t.dateRemboursement IS NOT NULL ORDER BY t.dateRemboursement DESC", Ticket.class)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Tickets Non Remboursés
    // ---------------------------

    public List<Ticket> findNonRefundedTickets() {
        return entityManager
                .createQuery("SELECT t FROM Ticket t WHERE t.dateRemboursement IS NULL ORDER BY t.dateAchat ASC", Ticket.class)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Tickets Annulés
    // ---------------------------

    public List<Ticket> findCancelledTickets() {
        return entityManager
                .createQuery("SELECT t FROM Ticket t WHERE t.dateAnnulation IS NOT NULL ORDER BY t.dateAnnulation DESC", Ticket.class)
                .getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Tickets au-Dessus d'un Prix
    // ---------------------------

    public List<Ticket> findTicketsAbovePrice(int minPrice) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> cq = cb.createQuery(Ticket.class);
        Root<Ticket> root = cq.from(Ticket.class);

        cq.select(root)
                .where(cb.gt(root.get("prix"), minPrice))
                .orderBy(cb.desc(root.get("prix")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Tickets en Gamme de Prix
    // ---------------------------

    public List<Ticket> findTicketsByPriceRange(Integer minPrice, Integer maxPrice) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> cq = cb.createQuery(Ticket.class);
        Root<Ticket> root = cq.from(Ticket.class);

        cq.select(root)
                .where(cb.between(root.get("prix"), minPrice, maxPrice))
                .orderBy(cb.asc(root.get("prix")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Tous les Tickets
    // ---------------------------

    public List<Ticket> findAllTickets() {
        return entityManager
                .createQuery("SELECT t FROM Ticket t ORDER BY t.dateAchat DESC", Ticket.class)
                .getResultList();
    }


    // ---------------------------
    // Quelques statistiques
    //----------------------------
    //Chiffre d'affaires total (hors annulations) :
    public Long getTotalRevenue() {
        return entityManager.createQuery(
                        "SELECT SUM(t.prix) FROM Ticket t WHERE t.dateAnnulation IS NULL", Long.class)
                .getSingleResult();
    }

    //Prix moyen d'un ticket vendu

    public Double getAverageTicketPrice() {
        return entityManager.createQuery(
                        "SELECT AVG(t.prix) FROM Ticket t WHERE t.dateAnnulation IS NULL", Double.class)
                .getSingleResult();
    }

    //Nombre de tickets par événement et par statut
    public List<Object[]> getCountByStatusForEvent(Long eventId) {
        return entityManager.createQuery(
                        "SELECT t.statut, COUNT(t) FROM Ticket t WHERE t.evenement.id = :eventId GROUP BY t.statut",
                        Object[].class)
                .setParameter("eventId", eventId)
                .getResultList();
    }

    //Statistiques Temporelles (Analyse des ventes)

    public List<Object[]> getSalesVolumeByDay() {
        return entityManager.createQuery(
                        "SELECT CAST(t.dateAchat AS date), COUNT(t) FROM Ticket t GROUP BY CAST(t.dateAchat AS date) ORDER BY 1 ASC",
                        Object[].class)
                .getResultList();
    }

    //Taux d'annulation (en pourcentage) :

    public Double getCancellationRate() {
        String query = "SELECT (COUNT(t) * 100.0) / (SELECT COUNT(t2) FROM Ticket t2) " +
                "FROM Ticket t WHERE t.dateAnnulation IS NOT NULL";
        return entityManager.createQuery(query, Double.class).getSingleResult();
    }



}