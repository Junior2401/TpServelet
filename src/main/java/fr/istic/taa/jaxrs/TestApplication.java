/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.istic.taa.jaxrs;

import java.util.HashSet;
import java.util.Set;

import fr.istic.taa.jaxrs.rest.*;
import fr.istic.taa.jaxrs.tools.JacksonConfig;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
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

        clazzes.add(AcceptHeaderOpenApiResource.class);
        clazzes.add(SwaggerResource.class);
        clazzes.add(PetResource.class);
        clazzes.add(JacksonConfig.class);
//        clazzes.add(AcceptHeaderOpenApiResource.class);
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
