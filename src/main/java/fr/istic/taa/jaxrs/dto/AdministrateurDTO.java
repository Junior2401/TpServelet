package fr.istic.taa.jaxrs.dto;

import fr.istic.taa.jaxrs.domain.Administrateur;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO pour créer, mettre à jour et exposer un administrateur")
public class AdministrateurDTO extends PersonneDTO {

    @Schema(description = "Rôle de l'administrateur", example = "SUPER_ADMIN")
    public String role;

    public AdministrateurDTO() {}

    public static AdministrateurDTO fromEntity(Administrateur admin) {
        AdministrateurDTO dto = new AdministrateurDTO();
        dto.id = admin.getId();
        dto.nom = admin.getNom();
        dto.prenom = admin.getPrenom();
        dto.email = admin.getEmail();
        dto.password = admin.getPassword();
        dto.role = admin.getRole();
        return dto;
    }

    public Administrateur toEntity() {
        Administrateur a = new Administrateur();
        a.setId(this.id);
        a.setNom(this.nom);
        a.setPrenom(this.prenom);
        a.setEmail(this.email);
        a.setPassword(this.password);
        a.setRole(this.role);
        return a;
    }
}

