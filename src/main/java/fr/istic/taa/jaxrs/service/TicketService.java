package fr.istic.taa.jaxrs.service;


import fr.istic.taa.jaxrs.dao.generic.EvenementDao;
import fr.istic.taa.jaxrs.dao.generic.TicketDao;
import fr.istic.taa.jaxrs.dao.generic.UtilisateurDao;
import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.tools.tools;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
public class TicketService {
    private final EvenementDao evenementDao;
    private final TicketDao ticketDao;
    private final UtilisateurDao utilisateurDao;

    public TicketService() {
        this.ticketDao = new TicketDao();
        this.utilisateurDao = new UtilisateurDao();
        this.evenementDao = new EvenementDao();
    }

    // -------------------------
    // CREATE
    // -------------------------
    public Ticket creerTicket(String numeroPlace, String place, tools.StatutTicket statut, Integer prix, LocalDateTime dateAchat, LocalDateTime dateAnnulation, LocalDateTime dateRemboursement, int evenement, int utilisateur) {
        int evenementId = evenement;
        int utilisateurId = utilisateur;
        Evenement evnt = evenementDao.findByIdEvenement(evenementId);
        Utilisateur user = utilisateurDao.findByIdUtilisateur(utilisateurId);
        Ticket ticket = new Ticket(null, numeroPlace, place, statut, prix, dateAchat, dateAnnulation, dateRemboursement, evnt, user);
        ticketDao.save(ticket);
        return ticket;
    }

    // -------------------------
    // READ ALL
    // -------------------------
    public List<Ticket> getAll() {
        return ticketDao.findAllTickets();
    }

    // -------------------------
    // READ BY ID
    // -------------------------
    public Ticket getById(Long id) {
        return ticketDao.findOne(id);
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public Ticket update(Long id, Ticket updated) {
        Ticket existing = getById(id);

        if (existing == null) {
            return null; // REST renverra 404
        }

        if (updated.getNumeroPlace() != null) {
            existing.setNumeroPlace(updated.getNumeroPlace());
        }

        if (updated.getPlace() != null) {
            existing.setPlace(updated.getPlace());
        }

        if (updated.getStatut() != null) {
            existing.setStatut(updated.getStatut());
        }

        if (updated.getPrix() != null) {
            existing.setPrix(updated.getPrix());
        }

        if (updated.getDateAchat() != null) {
            existing.setDateAchat(updated.getDateAchat());
        }

        if (updated.getDateAnnulation() != null) {
            existing.setDateAnnulation(updated.getDateAnnulation());
        }

        if (updated.getDateRemboursement() != null) {
            existing.setDateRemboursement(updated.getDateRemboursement());
        }

        return ticketDao.update(existing);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete(Long id) {
        Ticket ticket = getById(id);
        if (ticket != null) {
            ticketDao.delete(ticket);
        }
    }
}

