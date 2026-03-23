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
}