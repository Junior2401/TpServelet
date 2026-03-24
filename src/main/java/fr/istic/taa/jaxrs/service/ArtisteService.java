package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.dao.generic.ArtisteDao;
import fr.istic.taa.jaxrs.domain.Adresse;
import fr.istic.taa.jaxrs.domain.Artiste;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class ArtisteService {

    private final ArtisteDao artisteDao;

    public ArtisteService() {
        artisteDao = new ArtisteDao();
    }

    // -------------------------
    // CREATE
    // -------------------------
    public Artiste creerArtiste(String nom, String prenom, String email, String password,
                                Adresse adresse, String nomDeScene, String styleArtistique) {
        Artiste artiste = new Artiste(null, nom, prenom, email, password, adresse, nomDeScene, styleArtistique);
        artisteDao.save(artiste);
        return artiste;
    }

    // -------------------------
    // READ ALL
    // -------------------------
    public List<Artiste> getAll() {
        return artisteDao.findAll();
    }

    // -------------------------
    // READ BY ID
    // -------------------------
    public Artiste getById(Long id) {
        return artisteDao.findOne(id);
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public Artiste update(Long id, Artiste updated) {
        Artiste existing = getById(id);

        if (existing == null) {
            return null; // REST renverra 404
        }

        if (updated.getNom() != null) {
            existing.setNom(updated.getNom());
        }

        if (updated.getPrenom() != null) {
            existing.setPrenom(updated.getPrenom());
        }

        if (updated.getEmail() != null) {
            existing.setEmail(updated.getEmail());
        }

        if (updated.getPassword() != null) {
            existing.setPassword(updated.getPassword());
        }

        if (updated.getNomDeScene() != null) {
            existing.setNomDeScene(updated.getNomDeScene());
        }

        if (updated.getStyleArtistique() != null) {
            existing.setStyleArtistique(updated.getStyleArtistique());
        }

        return artisteDao.update(existing);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete(Long id) {
        Artiste artiste = getById(id);
        if (artiste != null) {
            artisteDao.delete(artiste);
        }
    }

    // -------------------------
    // MÉTHODES MÉTIER
    // -------------------------

    public List<Artiste> getByNomDeScene(String nomDeScene) {
        return artisteDao.findByNomDeScene(nomDeScene);
    }

    public Artiste getUniqueByNomDeScene(String nomDeScene) {
        return artisteDao.findUniqueByNomDeScene(nomDeScene);
    }

    public List<Artiste> getByStyleArtistique(String style) {
        return artisteDao.findByStyleArtistique(style);
    }

    public List<Artiste> getArtistesWithEvenements() {
        return artisteDao.findArtistesWithEvenements();
    }

    public List<Artiste> getArtistesWithManyEvenements(int min) {
        return artisteDao.findArtistesWithManyEvenements(min);
    }

    // -------------------------
    // STATISTIQUES
    // -------------------------

    /**
     * Nombre total d'artistes.
     */
    public Long getTotalArtistes() {
        return (long) artisteDao.findAll().size();
    }

    /**
     * Nombre d'artistes ayant participé à des événements.
     */
    public Long getNombreArtistesAvecEvenements() {
        return (long) getArtistesWithEvenements().size();
    }

    /**
     * Répartition des artistes par style artistique.
     */
    public java.util.Map<String, Long> getRepartitionParStyle() {
        return artisteDao.findAll().stream()
                .filter(a -> a.getStyleArtistique() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                        Artiste::getStyleArtistique,
                        java.util.stream.Collectors.counting()
                ));
    }
}
