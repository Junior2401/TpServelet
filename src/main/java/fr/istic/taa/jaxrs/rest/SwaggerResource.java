package fr.istic.taa.jaxrs.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class SwaggerResource {

    private static final Logger logger = Logger.getLogger(SwaggerResource.class.getName());

    @GET
    public Response getIndex() {
        String html = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="utf-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1" />
                <meta name="description" content="SwaggerUI" />
                <title>SwaggerUI</title>
                <link rel="stylesheet" href="swagger-ui.css" />
            </head>
            <body>
                <div id="swagger-ui"></div>
                <script src="swagger-ui-bundle.js" crossorigin></script>
                <script src="swagger-ui-standalone-preset.js" crossorigin></script>
                <script>
                    window.onload = function() {
                        const ui = SwaggerUIBundle({
                            url: '/openapi',
                            dom_id: '#swagger-ui',
                            deepLinking: true,
                            presets: [
                                SwaggerUIBundle.presets.apis,
                                SwaggerUIStandalonePreset
                            ],
                            plugins: [
                                SwaggerUIBundle.plugins.DownloadUrl
                            ],
                            layout: "StandaloneLayout"
                        });
                    };
                </script>
            </body>
            </html>
            """;
        return Response.ok(html).type("text/html").build();
    }

    @GET
    @Path("{path:.*}")
    public Response getFile(@PathParam("path") String path) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/resources/webjars/swagger-ui/5.17.14/" + path);
            if (is == null) {
                return Response.status(404).build();
            }
            byte[] content = is.readAllBytes();
            is.close();
            // Determine content type based on file extension
            String contentType = getContentType(path);
            return Response.ok(content).type(contentType).build();
        } catch (IOException e) {
            logger.warning("Error reading swagger file: " + path + " - " + e.getMessage());
            return Response.serverError().build();
        }
    }

    private String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".json")) return "application/json";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".svg")) return "image/svg+xml";
        return "application/octet-stream";
    }
}
