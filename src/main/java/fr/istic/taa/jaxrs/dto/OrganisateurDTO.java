package fr.istic.taa.jaxrs.dto;

import fr.istic.taa.jaxrs.domain.Organisateur;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO pour créer, mettre à jour et exposer un organisateur")
public class OrganisateurDTO extends PersonneDTO {

    @Schema(description = "Nom de la société", example = "ACME Corp")
    public String societe;

    @Schema(description = "Téléphone professionnel", example = "+33123456789")
    public String telephonePro;

    public OrganisateurDTO() {}

    public static OrganisateurDTO fromEntity(Organisateur organisateur) {
        OrganisateurDTO dto = new OrganisateurDTO();
        dto.id = organisateur.getId();
        dto.nom = organisateur.getNom();
        dto.prenom = organisateur.getPrenom();
        dto.email = organisateur.getEmail();
        dto.password = organisateur.getPassword();
        dto.societe = organisateur.getSociete();
        dto.telephonePro = organisateur.getTelephonePro();
        return dto;
    }

    public Organisateur toEntity() {
        Organisateur o = new Organisateur();
        o.setId(this.id);
        o.setNom(this.nom);
        o.setPrenom(this.prenom);
        o.setEmail(this.email);
        o.setPassword(this.password);
        o.setSociete(this.societe);
        o.setTelephonePro(this.telephonePro);
        return o;
    }
}

