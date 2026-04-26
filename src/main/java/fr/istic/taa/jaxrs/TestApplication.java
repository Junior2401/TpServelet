package fr.istic.taa.jaxrs;

import java.util.HashSet;
import java.util.Set;

import fr.istic.taa.jaxrs.rest.*;
import fr.istic.taa.jaxrs.tools.CORSFilter;
import fr.istic.taa.jaxrs.tools.JacksonConfig;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
        security = @SecurityRequirement(name = "bearerAuth"),
        info = @Info(title = "API Tickets", version = "1.0")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class TestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> clazzes = new HashSet<Class<?>>();

        // --- SECTION FILTRES ET CONFIG ---
        // Ajoutez votre filtre CORS ici pour qu'il soit actif !
        clazzes.add(CORSFilter.class);
        clazzes.add(JacksonConfig.class);

        // --- SECTION SWAGGER / OPENAPI ---
        clazzes.add(AcceptHeaderOpenApiResource.class);
        clazzes.add(OpenApiResource.class);
        clazzes.add(SwaggerResource.class);

        // --- SECTION RESSOURCES (CONTROLLERS) ---
        clazzes.add(TypeEvenenementResource.class);
        clazzes.add(OrganisateurResource.class);
        clazzes.add(UtilisateurResource.class);
        clazzes.add(TicketResource.class);
        clazzes.add(PersonneResource.class);
        clazzes.add(EvenementResource.class);
        clazzes.add(ArtisteResource.class);
        clazzes.add(AdministrateurResource.class);

        return clazzes;
    }
}