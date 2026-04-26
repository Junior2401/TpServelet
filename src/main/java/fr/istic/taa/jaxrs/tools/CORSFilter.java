package fr.istic.taa.jaxrs.tools;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        // Autorise l'origine d'Angular
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");

        // Autorise les méthodes HTTP classiques
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        // Autorise les headers personnalisés (si vous en utilisez)
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");

        // Permet au navigateur de mettre en cache la réponse CORS (gain de performance)
        responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");
    }
}