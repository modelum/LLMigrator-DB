package com.umu.prompts.infrastructure.api.rest.mapper;

import com.umu.prompts.domain.model.CodeMigrationRequest;
import com.umu.prompts.domain.model.CodeMigrationResponse;
import com.umu.prompts.domain.model.MigrationDbRequest;
import com.umu.prompts.domain.model.MigrationDbResponse;
import com.umu.prompts.domain.model.enums.DatabaseType;
import com.umu.prompts.domain.model.enums.FrameworkType;
import com.umu.prompts.domain.model.enums.ProgrammingLanguage;
import com.umu.prompts.domain.model.enums.SupportedLanguageLargeModels;
import com.umu.prompts.infrastructure.api.rest.dto.CodeMigrationRequestDto;
import com.umu.prompts.infrastructure.api.rest.dto.CodeMigrationResponseDto;
import com.umu.prompts.infrastructure.api.rest.dto.DatabaseMigrationRequestDto;
import com.umu.prompts.infrastructure.api.rest.dto.DatabaseMigrationResponseDto;

public class ControllerMapper {

  private ControllerMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static MigrationDbRequest fromDto(DatabaseMigrationRequestDto dto) {
    return MigrationDbRequest.builder()
        .sourceDatabase(DatabaseType.fromValue(dto.getSourceDatabaseType().getValue()))
        .targetDatabase(DatabaseType.fromValue(dto.getTargetDatabaseType().getValue()))
        .accessRequirements(dto.getAccessRequirements())
        .databaseSchema(dto.getDatabaseSchema())
        .databaseDocuments(dto.getDatabaseDocuments())
        .migrationRequirements(dto.getMigrationRequirements())
        .languageLargeModel(
            SupportedLanguageLargeModels.fromModelId(dto.getLanguageLargeModel().getModelId()))
        .build();
  }

  public static DatabaseMigrationResponseDto toDto(MigrationDbResponse request) {
    return DatabaseMigrationResponseDto.builder()
        .scriptForMigrateSchema(request.getScriptForMigrateSchema())
        .metadataForMigrateSchema(request.getMetadataForMigrateSchema())
        .guidelinesForValidateSchema(request.getGuidelinesForValidateSchema())
        .scriptForDataMigration(request.getScriptForDataMigration())
        .metadataForDataMigration(request.getMetadataForDataMigration())
        .scriptForDataValidation(request.getScriptForDataValidation())
        .metadataForDataValidation(request.getMetadataForDataValidation())
        .build();
  }

  public static CodeMigrationRequest fromDto(CodeMigrationRequestDto dto) {
    return CodeMigrationRequest.builder()
        .sourceDatabase(DatabaseType.fromValue(dto.getSourceDatabase().getValue()))
        .targetDatabase(DatabaseType.fromValue(dto.getTargetDatabase().getValue()))
        .sourceLanguage(ProgrammingLanguage.fromValue(dto.getSourceLanguage().getDisplayName()))
        .targetLanguage(ProgrammingLanguage.fromValue(dto.getTargetLanguage().getDisplayName()))
        .sourceFramework(FrameworkType.fromValue(dto.getSourceFramework().getValue()))
        .targetFramework(FrameworkType.fromValue(dto.getTargetFramework().getValue()))
        .domainModelCode(dto.getDomainModelCode())
        .repositoryCode(dto.getRepositoryCode())
        .businessLogicCode(dto.getBusinessLogicCode())
        .functionalRequirements(dto.getFunctionalRequirements())
        .targetSchema(dto.getTargetSchema())
        .outputFormat(dto.getOutputFormat())
        .usageExampleCode(dto.getUsageExampleCode())
        .languageLargeModel(
            SupportedLanguageLargeModels.fromModelId(dto.getLanguageLargeModel().getModelId()))
        .build();
  }

  public static CodeMigrationResponseDto toDto(CodeMigrationResponse response) {
    return CodeMigrationResponseDto.builder()
        .migratedDomainAndRepositoryCode(response.getMigratedDomainAndRepositoryCode())
        .migratedDomainAndRepositoryCodeExplication(
            response.getMigratedDomainAndRepositoryCodeExplication())
        .migratedServiceAndQueriesCode(response.getMigratedServiceAndQueriesCode())
        .migratedServiceAndQueriesCodeExplication(
            response.getMigratedServiceAndQueriesCodeExplication())
        .build();
  }
}
