# Configuration Swagger/OpenAPI - Guide d'utilisation

## ✅ Configuration effectuée

Votre projet a été configuré avec succès pour supporter Swagger/OpenAPI. Voici ce qui a été fait :

### 1. **Classe SwaggerResource créée**
   - Fichier : `src/main/java/fr/istic/taa/jaxrs/rest/SwaggerResource.java`
   - Cette classe sert les fichiers statiques de Swagger UI
   - Elle expose deux endpoints :
     - `GET /api/` : Retourne l'interface Swagger UI (index.html)
     - `GET /api/{path:.*}` : Retourne les fichiers statiques (CSS, JS, etc.)

### 2. **TestApplication mise à jour**
   - Ajout de `SwaggerResource.class` à la liste des ressources JAX-RS
   - `OpenApiResource.class` était déjà présent pour générer la description OpenAPI

### 3. **swagger-initializer.js configuré**
   - L'URL du fichier OpenAPI a été mise à jour pour pointer vers `http://localhost:8081/openapi.json`
   - Cela permet à Swagger UI d'afficher la documentation de votre API

## 🚀 Comment utiliser

### Démarrage de l'application

1. **Démarrer la base de données HSQLDB** :
   ```bash
   .\run-hsqldb-server.bat   # Windows
   # ou
   ./run-hsqldb-server.sh    # Linux/Mac
   ```

2. **Compiler le projet** :
   ```bash
   mvn clean compile
   ```

3. **Démarrer le serveur** :
   - Exécutez la classe `RestServer.java` (elle démarre sur le port 8081)

### Accéder à Swagger UI

Une fois le serveur démarré, vous pouvez accéder à l'interface Swagger UI à :

```
http://localhost:8081/api/
```

Cette interface affichera automatiquement la documentation de votre API en temps réel.

### Endpoints importants

- **OpenAPI Description** : `http://localhost:8081/openapi.json`
  - Format JSON standard OpenAPI 3.0
  - Décrit tous les endpoints, modèles, et paramètres

- **Swagger UI** : `http://localhost:8081/api/`
  - Interface interactive pour explorer l'API
  - Permet de tester les endpoints directement

## 📚 Documenter vos endpoints avec des annotations

Pour que vos endpoints soient correctement documentés dans Swagger, vous pouvez ajouter des annotations OpenAPI à vos ressources :

### Exemple pour une classe Resource :

```java
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/personnes")
@Tag(name = "Personnes", description = "Gestion des personnes")
public class PersonneResource {

    @GET
    @Path("/{id}")
    @Operation(summary = "Récupérer une personne", description = "Récupère une personne par son ID")
    @ApiResponse(responseCode = "200", description = "Personne trouvée",
        content = @Content(schema = @Schema(implementation = Personne.class)))
    @ApiResponse(responseCode = "404", description = "Personne non trouvée")
    public Personne getPersonneById(@PathParam("id") Long id) {
        // ...
    }
}
```

### Annotations principales à utiliser :

- **@Tag** : Groupe les endpoints par catégorie
- **@Operation** : Décrit l'opération (résumé, description)
- **@ApiResponse** : Documente les réponses possibles
- **@Parameter** : Documente les paramètres
- **@Schema** : Décrit la structure des modèles de données

## 🔗 Ressources utiles

- [OpenAPI Specification](https://spec.openapis.org/oas/v3.0.3)
- [Swagger Core Annotations](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.0-Java-projects)
- [SwaggerUI Documentation](https://swagger.io/tools/swagger-ui/)

## ✨ Prochaines étapes (optionnel)

Pour enrichir la documentation de votre API :

1. Ajoutez des annotations `@Operation`, `@ApiResponse`, etc. à vos ressources
2. Documentez vos modèles avec `@Schema`
3. Testez vos endpoints via l'interface Swagger UI

La configuration est maintenant complète et votre API est découvrable via OpenAPI !

