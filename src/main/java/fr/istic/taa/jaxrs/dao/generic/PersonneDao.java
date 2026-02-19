package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Personne;
import fr.istic.taa.jaxrs.domain.Utilisateur;

import java.util.List;

public class PersonneDao extends AbstractJpaDao<Long, Personne> {

    public PersonneDao() {
        setClazz(Personne.class);
    }

    public List<Personne> findAllPersonnes() {
        return entityManager
                .createQuery("SELECT personne FROM Personne personne ", Personne.class)
                .getResultList();
    }

    public Personne findByIdPersonne(int id) {
        return entityManager
                .createQuery("SELECT personne FROM Personne personne WHERE personne.id = :id", Personne.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}