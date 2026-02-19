package fr.istic.taa.jaxrs.dao.generic;
import fr.istic.taa.jaxrs.domain.Utilisateur;

import java.util.List;

public class UtilisateurDao extends AbstractJpaDao<Long, Utilisateur> {

    public UtilisateurDao() {
        setClazz(Utilisateur.class);
    }

    public List<Utilisateur> findAllUtilisateurs() {
        return entityManager
                .createQuery("SELECT users FROM Utilisateur users", Utilisateur.class)
                .getResultList();
    }

    public Utilisateur findByIdUtilisateur(int id) {
        return entityManager
                .createQuery("SELECT utilisateur FROM Utilisateur utilisateur WHERE utilisateur.id = :id", Utilisateur.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}