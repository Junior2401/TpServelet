package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Administrateur;

import java.util.List;

public class AdministrateurDao extends AbstractJpaDao<Long, Administrateur> {
    public AdministrateurDao() {
        setClazz(Administrateur.class);
    }

    public List<Administrateur> findAllAdmins() {
        return entityManager
                .createQuery("SELECT a FROM Administrateur a", Administrateur.class)
                .getResultList();
    }
}
