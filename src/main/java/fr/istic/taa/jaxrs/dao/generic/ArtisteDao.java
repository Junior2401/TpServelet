package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Artiste;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class ArtisteDao extends AbstractJpaDao<Long, Artiste> {

    public ArtisteDao() {
        setClazz(Artiste.class);
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Nom de Scène
    // ---------------------------

    public List<Artiste> findByNomDeScene(String nomDeScene) {
        return entityManager
                .createQuery("SELECT a FROM Artiste a WHERE LOWER(a.nomDeScene) LIKE LOWER(:nomDeScene) ORDER BY a.nomDeScene ASC", Artiste.class)
                .setParameter("nomDeScene", "%" + nomDeScene + "%")
                .getResultList();
    }

    public Artiste findUniqueByNomDeScene(String nomDeScene) {
        try {
            return entityManager
                    .createQuery("SELECT a FROM Artiste a WHERE LOWER(a.nomDeScene) = LOWER(:nomDeScene)", Artiste.class)
                    .setParameter("nomDeScene", nomDeScene)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Recherche par Style Artistique
    // ---------------------------

    public List<Artiste> findByStyleArtistique(String style) {
        return entityManager
                .createQuery("SELECT a FROM Artiste a WHERE LOWER(a.styleArtistique) LIKE LOWER(:style) ORDER BY a.nomDeScene ASC", Artiste.class)
                .setParameter("style", "%" + style + "%")
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Artistes avec Événements
    // ---------------------------

    public List<Artiste> findArtistesWithEvenements() {
        return entityManager
                .createQuery("SELECT DISTINCT a FROM Artiste a WHERE a.evenements IS NOT NULL AND SIZE(a.evenements) > 0 ORDER BY a.nomDeScene ASC", Artiste.class)
                .getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Artistes Célèbres
    // ---------------------------

    public List<Artiste> findArtistesWithManyEvenements(int minEvenements) {
        return entityManager
                .createQuery("SELECT a FROM Artiste a WHERE SIZE(a.evenements) >= :min ORDER BY SIZE(a.evenements) DESC", Artiste.class)
                .setParameter("min", minEvenements)
                .getResultList();
    }

    // ---------------------------
    // CRITERIA QUERY - Recherche par Nom Scène Préfixe
    // ---------------------------

    public List<Artiste> findArtistesByNomDeSceneStartingWith(String prefix) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Artiste> cq = cb.createQuery(Artiste.class);
        Root<Artiste> root = cq.from(Artiste.class);

        cq.select(root)
                .where(cb.like(cb.lower(root.get("nomDeScene")), prefix.toLowerCase() + "%"))
                .orderBy(cb.asc(root.get("nomDeScene")));

        return entityManager.createQuery(cq).getResultList();
    }

    // ---------------------------
    // MÉTHODES MÉTIER - Tous les Artistes
    // ---------------------------

    public List<Artiste> findAllArtistes() {
        return entityManager
                .createQuery("SELECT a FROM Artiste a ORDER BY a.nomDeScene ASC", Artiste.class)
                .getResultList();
    }

}