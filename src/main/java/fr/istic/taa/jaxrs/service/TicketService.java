package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.dao.generic.EvenementDao;
import fr.istic.taa.jaxrs.dao.generic.TicketDao;
import fr.istic.taa.jaxrs.dao.generic.UtilisateurDao;
import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.dto.TicketDTO;
import fr.istic.taa.jaxrs.tools.tools.StatutTicket;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
public class TicketService {

    private final TicketDao ticketDao = new TicketDao();
    private final UtilisateurDao utilisateurDao = new UtilisateurDao();
    private final EvenementDao evenementDao = new EvenementDao();

    // -------------------------
    // CRUD
    // -------------------------

    public List<Ticket> getAll() {
        return ticketDao.findAll();
    }

    public Ticket getById(Long id) {
        return ticketDao.findOne(id);
    }

    public Ticket create(Ticket ticket, Long evenementId, Long utilisateurId) {

        Evenement ev = evenementDao.findOne(evenementId);
        Utilisateur user = utilisateurDao.findOne(utilisateurId);

        ticket.setEvenement(ev);
        ticket.setUtilisateur(user);
        ticket.setDateAchat(LocalDateTime.now());

        ticketDao.save(ticket);
        return ticket;
    }

    public Ticket update(Long id, TicketDTO dto) {

        Ticket existing = ticketDao.findOne(id);
        if (existing == null) return null;

        // Mise à jour partielle
        if (dto.numeroPlace != null) existing.setNumeroPlace(dto.numeroPlace);
        if (dto.place != null) existing.setPlace(dto.place);
        if (dto.prix != null) existing.setPrix(dto.prix);
        if (dto.statut != null) existing.setStatut(StatutTicket.valueOf(dto.statut));
        if (dto.dateAnnulation != null) existing.setDateAnnulation(dto.dateAnnulation);
        if (dto.dateRemboursement != null) existing.setDateRemboursement(dto.dateRemboursement);

        // Mise à jour des relations si fournies
        if (dto.evenementId != null) {
            Evenement ev = evenementDao.findOne(dto.evenementId);
            existing.setEvenement(ev);
        }

        if (dto.utilisateurId != null) {
            Utilisateur user = utilisateurDao.findOne(dto.utilisateurId);
            existing.setUtilisateur(user);
        }

        return ticketDao.update(existing);
    }

    public void delete(Long id) {
        Ticket t = ticketDao.findOne(id);
        if (t != null) ticketDao.delete(t);
    }

    // -------------------------
    // MÉTHODES MÉTIER
    // -------------------------

    public List<Ticket> getByUtilisateur(Long userId) {
        return ticketDao.findByUtilisateur(userId);
    }

    public List<Ticket> getByEvenement(Long eventId) {
        return ticketDao.findByEvenement(eventId);
    }

    public List<Ticket> getByStatut(StatutTicket statut) {
        return ticketDao.findByStatut(statut);
    }

    public List<Ticket> getPurchasedAfter(LocalDateTime date) {
        return ticketDao.findPurchasedAfter(date);
    }

    public List<Ticket> getCancelledNotRefunded() {
        return ticketDao.findCancelledNotRefunded();
    }

    public List<Ticket> getAbovePrice(int price) {
        return ticketDao.findTicketsAbovePrice(price);
    }


    //--------------------------
    //Méthode statistique
    //--------------------------

    // -------------------------
    // STATISTIQUES
    // -------------------------

    /**
     * Calcule le chiffre d'affaires total pour un événement spécifique.
     * Exclut les tickets annulés pour refléter les revenus réels.
     */
    public Long getChiffreAffaireEvenement(Long eventId) {
        List<Ticket> tickets = ticketDao.findByEvenement(eventId);
        return tickets.stream()
                .filter(t -> t.getDateAnnulation() == null)
                .mapToLong(Ticket::getPrix)
                .sum();
    }

    /**
     * Calcule le taux de remplissage d'un événement.
     */
    public Double getTauxRemplissage(Long eventId) {
        Evenement ev = evenementDao.findOne(eventId);
        if (ev == null || ev.getCapacite() == null || ev.getCapacite() <= 0) return 0.0;

        long ticketsVendus = ticketDao.findByEvenement(eventId).stream()
                .filter(t -> t.getDateAnnulation() == null)
                .count();

        return (double) ticketsVendus / ev.getCapacite() * 100;
    }

    /**
     * Récupère le nombre de tickets par statut (VENDU, ANNULE, etc.)
     * Utile pour les graphiques de type Camembert (Pie Chart).
     */
    public java.util.Map<StatutTicket, Long> getRepartitionParStatut() {
        return ticketDao.findAll().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Ticket::getStatut,
                        java.util.stream.Collectors.counting()
                ));
    }

    /**
     * Calcule le montant total perdu à cause des annulations.
     */
    public Long getManqueAGagnerAnnulations() {
        return ticketDao.findAll().stream()
                .filter(t -> t.getDateAnnulation() != null)
                .mapToLong(Ticket::getPrix)
                .sum();
    }

    /**
     * Identifie les "Top Clients" (ceux qui ont acheté le plus de tickets).
     */
    public java.util.Map<String, Long> getTopAcheteurs(int limit) {
        return ticketDao.findAll().stream()
                .filter(t -> t.getUtilisateur() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                        t -> t.getUtilisateur().getNom() + " " + t.getUtilisateur().getPrenom(),
                        java.util.stream.Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(java.util.Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .collect(java.util.stream.Collectors.toMap(
                        java.util.Map.Entry::getKey,
                        java.util.Map.Entry::getValue,
                        (e1, e2) -> e1, java.util.LinkedHashMap::new
                ));
    }

    public Ticket creerTicket(String numeroPlace, String place, StatutTicket statut, int prix, LocalDateTime dateAchat, LocalDateTime dateAnnulation, LocalDateTime dateRemboursement, Long eventId, Long userId) {
        Evenement ev = evenementDao.findOne(eventId);
        Utilisateur user = utilisateurDao.findOne(userId);

        Ticket ticket = new Ticket();
        ticket.setNumeroPlace(numeroPlace);
        ticket.setPlace(place);
        ticket.setStatut(statut);
        ticket.setPrix(prix);
        ticket.setDateAchat(dateAchat);
        ticket.setDateAnnulation(dateAnnulation);
        ticket.setDateRemboursement(dateRemboursement);
        ticket.setEvenement(ev);
        ticket.setUtilisateur(user);

        ticketDao.save(ticket);
        return ticket;
    }
}