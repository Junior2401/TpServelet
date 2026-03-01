package fr.istic.taa.jaxrs.rest;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/api")
public class SwaggerResource {

    private static final Logger logger = Logger.getLogger(SwaggerResource.class.getName());

    @GET
    public byte[] Get1() {
        try {
            return Files.readAllBytes(FileSystems.getDefault().getPath("src/main/webapp/swagger-ui/dist/index.html"));
        } catch (IOException e) {
            logger.warning("Error reading swagger index.html: " + e.getMessage());
            return null;
        }
    }

    @GET
    @Path("{path:.*}")
    public byte[] Get(@PathParam("path") String path) {
        try {
            return Files.readAllBytes(FileSystems.getDefault().getPath("src/main/webapp/swagger-ui/dist/" + path));
        } catch (IOException e) {
            logger.warning("Error reading swagger file: " + path + " - " + e.getMessage());
            return null;
        }
    }

}

