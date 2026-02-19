package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Organisateur;

import java.util.List;

public class OrganisateurDao extends AbstractJpaDao<Long, Organisateur> {
    public OrganisateurDao() {
        setClazz(Organisateur.class);
    }

    public List<Organisateur> findAllOrganisateurs() {
        return entityManager
                .createQuery("SELECT org FROM Organisateur org", Organisateur.class)
                .getResultList();
    }

    public Organisateur findByIdTypeOrganisateur(int id) {
        return entityManager
                .createQuery("SELECT organisateur FROM Organisateur organisateur WHERE organisateur.id = :id", Organisateur.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
