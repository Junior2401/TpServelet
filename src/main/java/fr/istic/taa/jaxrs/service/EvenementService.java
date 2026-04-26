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
import java.util.Set;

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
    // CREATEa
    // -------------------------
    public Evenement creerEvenement(String libelle,
                                    String lieu,
                                    LocalDateTime date,
                                    Integer capacite,
                                    String description,
                                    tools.StatutEvenement statut,
                                    Long typeEvenementId,
                                    List<String> typesPlace) {

        TypeEvenement type = typeEvenementDao.findOne(typeEvenementId);

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

        evenement.setTypesPlace(typesPlace);
        evenementDao.save(evenement);
        return evenement;
    }

    // -------------------------
    // READ ALL
    // -------------------------
    public List<Evenement> getAll() {
        return evenementDao.findAll();
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

        if (updated.getTypesPlace() != null) {
            existing.setTypesPlace(updated.getTypesPlace());
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
    public Evenement ajouterOrganisateur(long evenementId, Long organisateurId) {

        Evenement evenement = evenementDao.findOne(evenementId);
        Organisateur organisateur = organisateurDao.findOne(organisateurId);

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
    public Evenement ajouterArtiste(long evenementId, Long artisteId) {

        Evenement evenement = evenementDao.findOne(evenementId);
        Artiste artiste = artisteDao.findOne(artisteId);

        if (evenement == null || artiste == null) {
            throw new IllegalArgumentException("Évènement ou artiste introuvable");
        }

        evenement.getArtistes().add(artiste);
        artiste.getEvenements().add(evenement);

        evenementDao.update(evenement);
        return evenement;
    }

    // -------------------------
    // MÉTHODES MÉTIER
    // -------------------------

    public List<Evenement> getByStatut(tools.StatutEvenement statut) {
        return evenementDao.findByStatut(statut);
    }

    public List<Evenement> getByLibelle(String libelle) {
        return evenementDao.findByLibelle(libelle);
    }

    public List<Evenement> getByLieu(String lieu) {
        return evenementDao.findByLieu(lieu);
    }

    public List<Evenement> getByDateRange(LocalDateTime debut, LocalDateTime fin) {
        return evenementDao.findByDateRange(debut, fin);
    }

    public List<Evenement> getUpcoming() {
        return evenementDao.findUpcoming(LocalDateTime.now());
    }

    public List<Evenement> getPast() {
        return evenementDao.findPast(LocalDateTime.now());
    }

    public List<Evenement> getByTypeEvenement(Long typeId) {
        return evenementDao.findByTypeEvenementId(typeId);
    }

    public List<Evenement> getByCapaciteMin(Integer min) {
        return evenementDao.findByCapaciteMin(min);
    }

    public List<Evenement> getByCapaciteRange(Integer min, Integer max) {
        return evenementDao.findByCapaciteRange(min, max);
    }

    public List<Evenement> getByOrganisateur(Long orgId) {
        return evenementDao.findAll().stream()
                .filter(e -> e.getOrganisateurs().stream().anyMatch(o -> o.getId().equals(orgId)))
                .toList();
    }

    public List<Evenement> getByArtiste(Long artisteId) {
        return evenementDao.findAll().stream()
                .filter(e -> e.getArtistes().stream().anyMatch(a -> a.getId().equals(artisteId)))
                .toList();
    }

    public List<Evenement> getByDate(java.time.LocalDate date) {
        return evenementDao.findAll().stream()
                .filter(e -> e.getDate() != null && e.getDate().toLocalDate().equals(date))
                .toList();
    }

    // -------------------------
    // STATISTIQUES
    // -------------------------

    /**
     * Calcule la capacité totale de tous les événements.
     */
    public Long getTotalCapacity() {
        return evenementDao.findAll().stream()
                .mapToLong(e -> e.getCapacite() != null ? e.getCapacite() : 0)
                .sum();
    }

    /**
     * Calcule la capacité moyenne des événements.
     */
    public Double getAverageCapacity() {
        List<Evenement> events = evenementDao.findAll();
        if (events.isEmpty()) return 0.0;
        return events.stream()
                .filter(e -> e.getCapacite() != null)
                .mapToInt(Evenement::getCapacite)
                .average()
                .orElse(0.0);
    }

    /**
     * Récupère le nombre d'événements par statut.
     */
    public java.util.Map<tools.StatutEvenement, Long> getRepartitionParStatut() {
        return evenementDao.findAll().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Evenement::getStatut,
                        java.util.stream.Collectors.counting()
                ));
    }

    /**
     * Récupère le nombre d'événements par type.
     */
    public java.util.Map<String, Long> getRepartitionParType() {
        return evenementDao.findAll().stream()
                .filter(e -> e.getTypeEvenement() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                        e -> e.getTypeEvenement().getLibelle(),
                        java.util.stream.Collectors.counting()
                ));
    }

    public Long getTotalEvents() {
        return (long) evenementDao.findAll().size();
    }

    public java.util.Map<String, Long> getEventsByMonth(int year) {
        return evenementDao.findAll().stream()
                .filter(e -> e.getDate() != null && e.getDate().getYear() == year)
                .collect(java.util.stream.Collectors.groupingBy(
                        e -> java.time.format.DateTimeFormatter.ofPattern("MMMM").format(e.getDate()),
                        java.util.stream.Collectors.counting()
                ));
    }

    /**
     * Nombre d'événements à venir.
     */
    public Long getNombreEvenementsAVenir() {
        return (long) getUpcoming().size();
    }

    /**
     * Nombre d'événements passés.
     */
    public Long getNombreEvenementsPasses() {
        return (long) getPast().size();
    }
}