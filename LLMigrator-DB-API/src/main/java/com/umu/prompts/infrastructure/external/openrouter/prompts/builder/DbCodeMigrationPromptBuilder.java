package com.umu.prompts.infrastructure.external.openrouter.prompts.builder;

import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateCodeDatabaseRequestDto;
import com.umu.prompts.infrastructure.external.openrouter.prompts.format.FormatMigrationResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.stereotype.Component;

@Component
public final class DbCodeMigrationPromptBuilder {
  private StructuredOutputConverter<FormatMigrationResponse> format;

  public StructuredOutputConverter<FormatMigrationResponse> getFormatConverter() {
    if (format == null) {
      format = new BeanOutputConverter<>(FormatMigrationResponse.class);
    }
    return format;
  }

  /**
   * Establece el contexto general para la sesión de migración de código. No genera código, solo
   * prepara al LLM con toda la información necesaria.
   */
  public Prompt buildContextualPrompt(OpenrouterMigrateCodeDatabaseRequestDto request) {
    String systemString =
        """
         Actúa como un experto en migración de código. Tu objetivo es guiar la migración de una capa de persistencia completa desde una pila tecnológica a otra, asegurando que el código resultante sea idiomático y eficiente en el nuevo entorno.

         El proceso se dividirá en varias fases:
         1.  **Migración del Modelo de Dominio y Repositorios:** Migrar las clases de entidad y los patrones de acceso a datos (repositorios/DAO).
         2.  **Migración de Lógica de Negocio de los servicios de la aplicación** Adaptar los servicios que consumen los repositorios.

         Contexto General de la Migración:
         -   **Lenguaje Origen:** {sourceLanguage}
         -   **Lenguaje Destino:** {targetLanguage}
         -   **Framework Origen:** {sourceFramework}
         -   **Framework Destino:** {targetFramework}
         -   **Base de Datos Origen:** {sourceDatabase}
         -   **Base de Datos Destino:** {targetDatabase}
         -   **Arquitectura de Salida del código Deseada:** {outputFormat}
         -   **Esquema de Base de Datos Destino:** {targetSchema}
         -   **Requisitos Funcionales Clave:** {functionalRequirements}

         Instrucciones Generales:
         -   NO generes ninguna respuesta ahora. Asimila este contexto.
         -   En los siguientes pasos, te proporcionaré el código fuente específico a migrar.
         -   Asegúrate de que el código generado sea idiomático para el lenguaje y framework de destino.
         -   Presta especial atención a la gestión de transacciones, tipos de datos y convenciones de nomenclatura del ecosistema de destino.
         """;

    SystemMessage contextualMessage =
        (SystemMessage)
            new SystemPromptTemplate(systemString)
                .createMessage(
                    Map.of(
                        "sourceLanguage",
                        request.getSourceLanguage().name(),
                        "targetLanguage",
                        request.getTargetLanguage().name(),
                        "sourceFramework",
                        request.getSourceFramework().name(),
                        "targetFramework",
                        request.getTargetFramework().name(),
                        "sourceDatabase",
                        request.getSourceDatabase().name(),
                        "targetDatabase",
                        request.getTargetDatabase().name(),
                        "outputFormat",
                        request.getOutputFormat(),
                        "functionalRequirements",
                        request.getFunctionalRequirements(),
                        "targetSchema",
                        request.getTargetSchema()));

    // Este prompt no necesita UserMessage, solo establece el sistema.
    return new Prompt(List.of(contextualMessage));
  }

  /** Primer paso: Migra las clases del modelo de dominio (entidades) y los repositorios/DAOs. */
  public Prompt buildFirstPromptToMigrateDomainModelAndRepository(
      OpenrouterMigrateCodeDatabaseRequestDto request) {

    // Mensaje del sistema: Define el rol y las reglas para esta tarea específica.
    String systemString =
            """
            Actúa como un experto en migración de capas de persistencia, especializado en Modelos de Dominio y Repositorios.
            Tu tarea es migrar el código del modelo de dominio y del repositorio desde un entorno {sourceLanguage}/{sourceFramework}/{sourceDatabase} a {targetLanguage}/{targetFramework}/{targetDatabase}
    
            Instrucciones Clave:
    
            1.  **Modelo de Dominio:** Convierte las clases de entidad de {sourceLanguage} a {targetLanguage}. Presta atención a las anotaciones de mapeo (Ej: `@Entity`, `@Id` de JPA) y tradúcelas a sus equivalentes en `{targetFramework}`. Si el destino es NoSQL, adapta la estructura para reflejar documentos anidados o referencias.
            2.  **Repositorios/DAOs:** Migra las interfaces o clases de acceso a datos. Implementa los métodos CRUD estándar utilizando las abstracciones de `{targetFramework}` (Ej: `CrudRepository` de Spring Data, `DbSet` de EF Core).
            3.  **Cohesión:** Asegúrate de que los repositorios migrados operen correctamente sobre los modelos de dominio migrados. Los tipos de datos y nombres deben ser consistentes.
            4.  **Arquitectura:** Organiza el código generado siguiendo el patrón de diseño `{outputFormat}`.
            5. **Requisitos Funcionales:** Implementa los siguientes requisitos en el código migrado: `{functionalRequirements}`. Esto puede incluir paginación, joins, etc.
            6.  **IDs:** Asume que los identificadores (IDs) de las entidades deben mantenerse consistentes entre el origen y el destino.
            7. **Alineación del modelo del dominio con el esquema de la base de datos destino:** Asegúrate de que el modelo del dominio migrado esté alineado con el esquema de la base de datos destino.
            
            **Esquema de la base de datos destino:**
            ```
            {targetSchema}
            ```
    
            **IMPORTANTE**: La respuesta debe ser un JSON puro (RFC8259) que cumpla con el siguiente formato. No incluyas ` ```json ` ni ningún texto fuera del JSON.
            {format}
            """;

    SystemMessage systemMessage =
        (SystemMessage)
            new SystemPromptTemplate(systemString)
                .createMessage(
                    Map.of(
                        "sourceLanguage",
                        request.getSourceLanguage().name(),
                        "targetLanguage",
                        request.getTargetLanguage().name(),
                        "sourceFramework",
                        request.getSourceFramework().name(),
                        "targetFramework",
                        request.getTargetFramework().name(),
                        "sourceDatabase",
                        request.getSourceDatabase().name(),
                        "targetDatabase",
                        request.getTargetDatabase().name(),
                        "outputFormat",
                        request.getOutputFormat(),
                        "format",
                        getFormatConverter().getFormat(),
                        "functionalRequirements",
                        request.getFunctionalRequirements()));

    // Mensaje del usuario: Proporciona el código concreto a migrar.
    String userString =
        """
        Migra el siguiente código de {sourceFramework} a {targetFramework}.

        **Código del Modelo de Dominio Origen (en {sourceLanguage}):**
        ```
        {domainModelCode}
        ```

        **Código del Repositorio Origen (en {sourceLanguage}):**
        ```
        {repositoryCode}
        ```

        Genera el código equivalente para `{targetFramework}` en el lenguaje {targetLanguage}, siguiendo todas las instrucciones y el formato de salida especificado. En la explicación, detalla los cambios clave en las anotaciones, la estructura del repositorio y cualquier consideración importante.
        """;

    UserMessage userMessage =
        (UserMessage)
            new PromptTemplate(userString)
                .createMessage(
                    Map.of(
                        "sourceLanguage", request.getSourceLanguage().name(),
                        "targetLanguage", request.getTargetLanguage().name(),
                        "sourceFramework", request.getSourceFramework().name(),
                        "targetFramework", request.getTargetFramework().name(),
                        "domainModelCode", request.getDomainModelCode(),
                        "repositoryCode", request.getRepositoryCode()));

    return new Prompt(List.of(systemMessage, userMessage));
  }

  /**
   * Segundo paso: Migra la lógica de servicios y las consultas personalizadas, asumiendo que el
   * modelo y repositorio ya fueron migrados.
   */
  public Prompt buildPromptToMigrateServiceAndCustomQueries(
      OpenrouterMigrateCodeDatabaseRequestDto request, String scriptForDomainAndRepo) {

    // Mensaje del sistema: Define el rol para esta tarea avanzada.
    String systemString =
        """
        Actúa como un experto en migración de lógica de negocio entre diferentes lenguajes y frameworks.

        Tu tarea es migrar código de perteneciente a la capa de servicio, asumiendo que el Modelo de Dominio y los Repositorios ya han sido migrados a `{targetLanguage}` y `{targetFramework}`. Debes adaptar el código que *utiliza* estos repositorios. El código que utiliza estos repositorios y el modelo de dominio ya migrado es el siguiente:

        {scriptForDomainAndRepo}

        Instrucciones Clave:
        1.  **Adaptación de Servicios:** migra el código de los servicios para que utilice las nuevas interfaces de repositorio migradas en {targetLanguage}. Presta especial atención a la gestión de transacciones (Ej: `@Transactional` en Spring).
        2.  **Requisitos Funcionales:** Implementa los siguientes requisitos en el código migrado: `{functionalRequirements}`. Esto puede incluir paginación, ordenación, proyecciones, etc.
        3.  **Ejemplo de Uso:** Utiliza el ejemplo de uso proporcionado para guiar y verificar tu migración, mostrando cómo se llamaría al nuevo código.

        IMPORTANTE: La respuesta debe ser un JSON puro (RFC8259) que cumpla con el siguiente formato. No incluyas ` ```json ` ni ningún texto fuera del JSON.
        {format}
        """;

    SystemMessage systemMessage =
        (SystemMessage)
            new SystemPromptTemplate(systemString)
                .createMessage(
                    Map.of(
                        "targetLanguage",
                        request.getTargetLanguage().name(),
                        "targetFramework",
                        request.getTargetFramework().name(),
                        "functionalRequirements",
                        request.getFunctionalRequirements(),
                        "format",
                        getFormatConverter().getFormat(),
                        "scriptForDomainAndRepo",
                        scriptForDomainAndRepo));

    // Mensaje del usuario: Proporciona el código de alto nivel y las consultas
    // específicas.
    String userString =
        """
        Continuando con la migración a {targetFramework}, ahora migra el código referente a la lógica de negocio del lenguaje {sourceLanguage} al lenguaje {targetLanguage}.

        **Código de la capa de servicio en {sourceLanguage}/{sourceFramework}**
        ```
        {businessLogicCode}
        ```

        **Ejemplo de Uso en la Capa de Servicio Origen (opcional, pero muy útil):**
        ```
        {usageExampleCode}
        ```

        Genera el código equivalente para `{targetFramework}` en {targetLanguage}. En la explicación, justifica tus decisiones para la traducción de cada consulta, cómo se integra con los repositorios y cómo se ha implementado la gestión de transacciones o los requisitos funcionales.
        """;

    UserMessage userMessage =
        (UserMessage)
            new PromptTemplate(userString)
                .createMessage(
                    Map.of(
                        "sourceLanguage",
                        request.getSourceLanguage().name(),
                        "targetLanguage",
                        request.getTargetLanguage().name(),
                        "sourceFramework",
                        request.getSourceFramework().name(),
                        "targetFramework",
                        request.getTargetFramework().name(),
                        "businessLogicCode",
                        request.getBusinessLogicCode(),
                        "usageExampleCode",
                        Objects.toString(
                            request.getUsageExampleCode(),
                            "No se proporcionó un ejemplo de uso.")));

    return new Prompt(List.of(systemMessage, userMessage));
  }
}
