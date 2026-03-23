package fr.istic.taa.jaxrs.dto;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO pour créer, mettre à jour et exposer un utilisateur")
public class UtilisateurDTO extends PersonneDTO {

    @Schema(description = "Numéro de téléphone de l'utilisateur", example = "+33612345678")
    public String telephone;

    public UtilisateurDTO() {}

    public static UtilisateurDTO fromEntity(Utilisateur utilisateur) {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.id = utilisateur.getId();
        dto.nom = utilisateur.getNom();
        dto.prenom = utilisateur.getPrenom();
        dto.email = utilisateur.getEmail();
        dto.password = utilisateur.getPassword();
        dto.telephone = utilisateur.getTelephone();
        return dto;
    }

    public Utilisateur toEntity() {
        Utilisateur u = new Utilisateur();
        u.setId(this.id);
        u.setNom(this.nom);
        u.setPrenom(this.prenom);
        u.setEmail(this.email);
        u.setPassword(this.password);
        u.setTelephone(this.telephone);
        return u;
    }
}

