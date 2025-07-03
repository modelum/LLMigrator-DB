package com.umu.prompts.infrastructure.external.openrouter.adapter;

import com.umu.prompts.domain.model.CodeMigrationRequest;
import com.umu.prompts.domain.model.MigrationDbRequest;
import com.umu.prompts.domain.ports.external.LanguageLargeModelPort;
import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateCodeDatabaseRequestDto;
import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateCodeDatabaseResponseDto;
import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateDatabaseRequestDto;
import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateDatabaseResponseDto;
import com.umu.prompts.infrastructure.external.openrouter.mapper.OpenrouterMapper;
import com.umu.prompts.infrastructure.external.openrouter.parser.SafeJsonParser;
import com.umu.prompts.infrastructure.external.openrouter.prompts.builder.DbCodeMigrationPromptBuilder;
import com.umu.prompts.infrastructure.external.openrouter.prompts.builder.DbMigrationPromptBuilder;
import com.umu.prompts.infrastructure.external.openrouter.prompts.format.FormatMigrationResponse;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LanguageLargeModelAdapter implements LanguageLargeModelPort {

  private final Map<String, ChatClient> chatClients;
  private final ChatMemory chatMemory;
  private final DbMigrationPromptBuilder dbMigrationPromptBuilder;
  private final DbCodeMigrationPromptBuilder dbCodeMigrationPromptBuilder;
  private final SafeJsonParser safeJsonParser;

  @Override
  public OpenrouterMigrateDatabaseResponseDto migrateDatabase(MigrationDbRequest infoForMigration) {

    OpenrouterMigrateDatabaseRequestDto request =
        OpenrouterMapper.fromDomainModel(infoForMigration);
    ChatClient chatClient = chatClients.get(request.getLanguageLargeModel().getModelId());
    if (chatClient == null) {
      throw new IllegalArgumentException(
          "No se ha encontrado un cliente de chat para el modelo: "
              + request.getLanguageLargeModel().getModelId());
    }

    String conversationId = UUID.randomUUID().toString();

    // 1. Contextualizar
    Prompt promptToContext = dbMigrationPromptBuilder.buildContextualPrompt(request);
    chatClient
        .prompt(promptToContext)
        .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call();

    // 2. Migrar esquema
    Prompt promptToMigrateSchema =
        dbMigrationPromptBuilder.buildFirstPromptToMigrateSchema(request);
    String rawResponseForMigrateSchema =
        getChatResponseContent(chatClient, promptToMigrateSchema, conversationId);
    FormatMigrationResponse responseForMigrateSchema =
        safeJsonParser.parse(rawResponseForMigrateSchema);

    // 3. Validar esquema
    Prompt promptToValidateSchema =
        dbMigrationPromptBuilder.buildPromptToValidateSchema(
            request, responseForMigrateSchema.script());
    String guidelinesForValidateSchema =
        getChatResponseContent(chatClient, promptToValidateSchema, conversationId);

    // 4. Migrar datos
    Prompt promptToMigrateData =
        dbMigrationPromptBuilder.buildSecondPromptToMigrateData(
            request, responseForMigrateSchema.script());
    String rawResponseForMigrateData =
        getChatResponseContent(chatClient, promptToMigrateData, conversationId);
    FormatMigrationResponse responseForMigrateData =
        safeJsonParser.parse(rawResponseForMigrateData);

    // 5. Validar migración de datos
    Prompt promptToValidateMigration =
        dbMigrationPromptBuilder.buildThirdPromptToValidateMigration(
            request, responseForMigrateData.script());
    String rawResponseForValidateMigration =
        getChatResponseContent(chatClient, promptToValidateMigration, conversationId);
    FormatMigrationResponse responseForValidateMigration =
        safeJsonParser.parse(rawResponseForValidateMigration);

    // Limpiar la memoria asociada a esta conversación
    chatMemory.clear(conversationId);

    return OpenrouterMigrateDatabaseResponseDto.builder()
        .scriptForMigrateSchema(responseForMigrateSchema.script())
        .metadataForMigrateSchema(responseForMigrateSchema.scriptExplication())
        .guidelinesForValidateSchema(guidelinesForValidateSchema)
        .scriptForDataMigration(responseForMigrateData.script())
        .metadataForDataMigration(responseForMigrateData.scriptExplication())
        .scriptForDataValidation(responseForValidateMigration.script())
        .metadataForDataValidation(responseForValidateMigration.scriptExplication())
        .build();
  }

  @Override
  public OpenrouterMigrateCodeDatabaseResponseDto migrateDatabaseCode(
      CodeMigrationRequest infoForMigration) {
    OpenrouterMigrateCodeDatabaseRequestDto request =
        OpenrouterMapper.fromDomainModel(infoForMigration);
    ChatClient chatClient = chatClients.get(request.getLanguageLargeModel().getModelId());
    if (chatClient == null) {
      throw new IllegalArgumentException(
          "No se ha encontrado un cliente de chat para el modelo: "
              + request.getLanguageLargeModel().getModelId());
    }

    String conversationId = UUID.randomUUID().toString();

    // 1. Contextualizar
    Prompt promptToContext = dbCodeMigrationPromptBuilder.buildContextualPrompt(request);
    chatClient
        .prompt(promptToContext)
        .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call();

    // 2. Migrar modelo de dominio y repositorios
    Prompt promptToMigrateDomainAndRepo =
        dbCodeMigrationPromptBuilder.buildFirstPromptToMigrateDomainModelAndRepository(request);
    String rawResponseForDomainAndRepo =
        getChatResponseContent(chatClient, promptToMigrateDomainAndRepo, conversationId);
    FormatMigrationResponse responseForDomainAndRepo =
        safeJsonParser.parse(rawResponseForDomainAndRepo);

    // 3. Migrar servicios y consultas personalizadas
    Prompt promptToMigrateServiceAndQueries =
        dbCodeMigrationPromptBuilder.buildPromptToMigrateServiceAndCustomQueries(
            request, responseForDomainAndRepo.script());
    String rawResponseForServiceAndQueries =
        getChatResponseContent(chatClient, promptToMigrateServiceAndQueries, conversationId);
    FormatMigrationResponse responseForServiceAndQueries =
        safeJsonParser.parse(rawResponseForServiceAndQueries);

    // Limpiar la memoria asociada a esta conversación
    chatMemory.clear(conversationId);

    return OpenrouterMigrateCodeDatabaseResponseDto.builder()
        .migratedDomainAndRepositoryCode(responseForDomainAndRepo.script())
        .migratedDomainAndRepositoryCodeExplication(responseForDomainAndRepo.scriptExplication())
        .migratedServiceAndQueriesCode(responseForServiceAndQueries.script())
        .migratedServiceAndQueriesCodeExplication(responseForServiceAndQueries.scriptExplication())
        .build();
  }

  /**
   * Método auxiliar para obtener el contenido de la respuesta. Si el contenido es null o vacío, se
   * lanza una excepción con mensaje detallado.
   */
  private String getChatResponseContent(
      ChatClient chatClient, Prompt prompt, String conversationId) {
    String content =
        chatClient
            .prompt(prompt)
            .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
            .call()
            .content();

    if (content == null || content.trim().isEmpty()) {
      throw new RuntimeException("No se ha recibido contenido para el prompt: " + prompt);
    }
    return content;
  }
}
