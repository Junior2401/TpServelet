package fr.istic.taa.jaxrs.service;


import fr.istic.taa.jaxrs.dao.generic.ArtisteDao;
import fr.istic.taa.jaxrs.dao.generic.EvenementDao;
import fr.istic.taa.jaxrs.dao.generic.OrganisateurDao;
import fr.istic.taa.jaxrs.dao.generic.TypeEvenementDao;
import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.TypeEvenement;
import fr.istic.taa.jaxrs.tools.tools;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
public class EvenementService {

    private final EvenementDao evenementDao;
    private final TypeEvenementDao typeEvenementDao;
    private final OrganisateurDao organisateurDao;
    private final ArtisteDao artisteDao;

    public EvenementService() {
        evenementDao = new EvenementDao();
        typeEvenementDao = new TypeEvenementDao();
        organisateurDao = new OrganisateurDao();
        artisteDao = new ArtisteDao();
    }

    // -------------------------
    // CREATE
    // -------------------------
    public Evenement creerEvenement(String libelle,
                                    String lieu,
                                    LocalDateTime date,
                                    Integer capacite,
                                    String description,
                                    tools.StatutEvenement statut,
                                    int typeEvenementId) {

        TypeEvenement type = typeEvenementDao.findByIdTypeEvenement(typeEvenementId);

        if (type == null) {
            throw new IllegalArgumentException("Type d'évènement introuvable : " + typeEvenementId);
        }

        Evenement evenement = new Evenement(
                null,
                libelle,
                lieu,
                date,
                capacite,
                description,
                statut,
                type
        );

        evenementDao.save(evenement);
        return evenement;
    }

    // -------------------------
    // READ ALL
    // -------------------------
    public List<Evenement> getAll() {
        return evenementDao.findAllEvenements();
    }

    // -------------------------
    // READ BY ID
    // -------------------------
    public Evenement getById(Long id) {
        return evenementDao.findOne(id);
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public Evenement update(Long id, Evenement updated) {
        Evenement existing = getById(id);

        if (existing == null) {
            return null; // REST renverra 404
        }

        if (updated.getLibelle() != null) {
            existing.setLibelle(updated.getLibelle());
        }

        if (updated.getLieu() != null) {
            existing.setLieu(updated.getLieu());
        }

        if (updated.getDate() != null) {
            existing.setDate(updated.getDate());
        }

        if (updated.getCapacite() != null) {
            existing.setCapacite(updated.getCapacite());
        }

        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }

        if (updated.getStatut() != null) {
            existing.setStatut(updated.getStatut());
        }

        return evenementDao.update(existing);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete(Long id) {
        Evenement evenement = getById(id);
        if (evenement != null) {
            evenementDao.delete(evenement);
        }
    }

    // -------------------------
    // AJOUTER UN ORGANISATEUR A UN EVENEMENT
    // -------------------------
    public Evenement ajouterOrganisateur(int evenementId, int organisateurId) {

        Evenement evenement = evenementDao.findByIdEvenement(evenementId);
        Organisateur organisateur = organisateurDao.findByIdTypeOrganisateur(organisateurId);

        if (evenement == null || organisateur == null) {
            throw new IllegalArgumentException("Évènement ou organisateur introuvable");
        }

        evenement.getOrganisateurs().add(organisateur);
        organisateur.getEvenements().add(evenement);

        evenementDao.update(evenement);
        return evenement;
    }

    // -------------------------
    // AJOUTER UN ARTISTE A UN EVENEMENT
    // -------------------------
    public Evenement ajouterArtiste(int evenementId, int artisteId) {

        Evenement evenement = evenementDao.findByIdEvenement(evenementId);
        Artiste artiste = artisteDao.findArtisteByIdArtistes(artisteId);

        if (evenement == null || artiste == null) {
            throw new IllegalArgumentException("Évènement ou artiste introuvable");
        }

        evenement.getArtistes().add(artiste);
        artiste.getEvenements().add(evenement);

        evenementDao.update(evenement);
        return evenement;
    }
}