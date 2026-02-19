package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Evenement;

import java.util.List;

public class EvenementDao extends AbstractJpaDao<Long, Evenement> {

    public EvenementDao() {
        setClazz(Evenement.class);
    }

    public List<Evenement> findAllEvenements() {
        return entityManager.createQuery(
                "SELECT e FROM Evenement e " +
                        "LEFT JOIN FETCH e.organisateurs " +
                        "LEFT JOIN FETCH e.artistes " +
                        "LEFT JOIN FETCH e.typeEvenement",
                Evenement.class
        ).getResultList();
    }
/*public List<Evenement> findAllEvenements() {
    List<Evenement> evenements = entityManager
            .createQuery("SELECT e FROM Evenement e", Evenement.class)
            .getResultList();

    // Forcer le chargement des relations
    for (Evenement e : evenements) {
        e.getOrganisateurs().size();
        e.getArtistes().size();
    }

    return evenements;
}*/

    public Evenement findByIdEvenement(int id) {
        return entityManager
                .createQuery("SELECT evenement FROM Evenement evenement WHERE evenement.id = :id", Evenement.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}