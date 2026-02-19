package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Artiste;

import java.util.List;

public class ArtisteDao extends AbstractJpaDao<Long, Artiste> {

    public ArtisteDao() {
        setClazz(Artiste.class);
    }

    public List<Artiste> findAllArtistes() {
        return entityManager
                .createQuery("SELECT artiste FROM Artiste artiste ", Artiste.class)
                .getResultList();
    }

    public Artiste findArtisteByIdArtistes(int id) {
        return entityManager.find(Artiste.class, id);
    }
}