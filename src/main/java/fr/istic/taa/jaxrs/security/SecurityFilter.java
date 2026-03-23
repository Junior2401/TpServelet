package fr.istic.taa.jaxrs.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    private static final String API_TOKEN = "SECRET123"; // à mettre dans un fichier config

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String auth = requestContext.getHeaderString("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            abort(requestContext);
            return;
        }

        String token = auth.substring("Bearer ".length());

        if (!API_TOKEN.equals(token)) {
            abort(requestContext);
        }
    }

    private void abort(ContainerRequestContext ctx) {
        ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("Token invalide ou manquant")
                .build());
    }
}