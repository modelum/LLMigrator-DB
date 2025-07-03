package com.umu.prompts.infrastructure.external.openrouter.mapper;

import com.umu.prompts.domain.model.CodeMigrationRequest;
import com.umu.prompts.domain.model.CodeMigrationResponse;
import com.umu.prompts.domain.model.MigrationDbRequest;
import com.umu.prompts.domain.model.MigrationDbResponse;
import com.umu.prompts.infrastructure.api.rest.dto.enums.DatabaseTypeDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.FrameworkTypeDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.ProgrammingLanguageDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.SupportedLanguageLargeModelsDto;
import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateCodeDatabaseRequestDto;
import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateCodeDatabaseResponseDto;
import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateDatabaseRequestDto;
import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateDatabaseResponseDto;

public class OpenrouterMapper {

  private OpenrouterMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static OpenrouterMigrateDatabaseRequestDto fromDomainModel(
      MigrationDbRequest migrationDbRequest) {
    return OpenrouterMigrateDatabaseRequestDto.builder()
        .sourceDatabaseType(
            DatabaseTypeDto.fromValue(migrationDbRequest.getSourceDatabase().getValue()))
        .targetDatabaseType(
            DatabaseTypeDto.fromValue(migrationDbRequest.getTargetDatabase().getValue()))
        .accessRequirements(migrationDbRequest.getAccessRequirements())
        .databaseSchema(migrationDbRequest.getDatabaseSchema())
        .databaseDocuments(migrationDbRequest.getDatabaseDocuments())
        .migrationRequirements(migrationDbRequest.getMigrationRequirements())
        .languageLargeModel(
            SupportedLanguageLargeModelsDto.fromValue(
                migrationDbRequest.getLanguageLargeModel().getModelId()))
        .build();
  }

  public static MigrationDbResponse toDomainModel(OpenrouterMigrateDatabaseResponseDto response) {
    return MigrationDbResponse.builder()
        .scriptForMigrateSchema(response.getScriptForMigrateSchema())
        .metadataForMigrateSchema(response.getMetadataForMigrateSchema())
        .guidelinesForValidateSchema(response.getGuidelinesForValidateSchema())
        .scriptForDataMigration(response.getScriptForDataMigration())
        .metadataForDataMigration(response.getMetadataForDataMigration())
        .scriptForDataValidation(response.getScriptForDataValidation())
        .metadataForDataValidation(response.getMetadataForDataValidation())
        .build();
  }

  public static OpenrouterMigrateCodeDatabaseRequestDto fromDomainModel(
      CodeMigrationRequest request) {
    return OpenrouterMigrateCodeDatabaseRequestDto.builder()
        .sourceDatabase(DatabaseTypeDto.fromValue(request.getSourceDatabase().getValue()))
        .targetDatabase(DatabaseTypeDto.fromValue(request.getTargetDatabase().getValue()))
        .sourceLanguage(
            ProgrammingLanguageDto.fromValue(request.getSourceLanguage().getDisplayName()))
        .targetLanguage(
            ProgrammingLanguageDto.fromValue(request.getTargetLanguage().getDisplayName()))
        .sourceFramework(FrameworkTypeDto.fromValue(request.getSourceFramework().getValue()))
        .targetFramework(FrameworkTypeDto.fromValue(request.getTargetFramework().getValue()))
        .domainModelCode(request.getDomainModelCode())
        .repositoryCode(request.getRepositoryCode())
        .businessLogicCode(request.getBusinessLogicCode())
        .functionalRequirements(request.getFunctionalRequirements())
        .targetSchema(request.getTargetSchema())
        .outputFormat(request.getOutputFormat())
        .usageExampleCode(request.getUsageExampleCode())
        .languageLargeModel(
            SupportedLanguageLargeModelsDto.fromValue(request.getLanguageLargeModel().getModelId()))
        .build();
  }

  public static CodeMigrationResponse toDomainModel(
      OpenrouterMigrateCodeDatabaseResponseDto response) {
    return CodeMigrationResponse.builder()
        .migratedDomainAndRepositoryCode(response.getMigratedDomainAndRepositoryCode())
        .migratedDomainAndRepositoryCodeExplication(
            response.getMigratedDomainAndRepositoryCodeExplication())
        .migratedServiceAndQueriesCode(response.getMigratedServiceAndQueriesCode())
        .migratedServiceAndQueriesCodeExplication(
            response.getMigratedServiceAndQueriesCodeExplication())
        .build();
  }
}
