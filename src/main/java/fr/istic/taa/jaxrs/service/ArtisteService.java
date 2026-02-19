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
        return artisteDao.findAllArtistes();
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
}
