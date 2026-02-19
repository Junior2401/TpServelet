package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Ticket;

import java.util.List;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {

    public TicketDao() {
        setClazz(Ticket.class);
    }

    public List<Ticket> findAllTickets() {
        return entityManager
                .createQuery("SELECT ticket FROM Ticket ticket", Ticket.class)
                .getResultList();
    }
}