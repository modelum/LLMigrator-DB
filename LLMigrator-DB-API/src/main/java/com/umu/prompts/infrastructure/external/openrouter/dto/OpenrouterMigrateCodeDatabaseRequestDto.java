package com.umu.prompts.infrastructure.external.openrouter.dto;

import com.umu.prompts.infrastructure.api.rest.dto.enums.DatabaseTypeDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.FrameworkTypeDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.ProgrammingLanguageDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.SupportedLanguageLargeModelsDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenrouterMigrateCodeDatabaseRequestDto {
  private final DatabaseTypeDto sourceDatabase;
  private final DatabaseTypeDto targetDatabase;
  private final ProgrammingLanguageDto sourceLanguage;
  private final ProgrammingLanguageDto targetLanguage;
  private final FrameworkTypeDto sourceFramework;
  private final FrameworkTypeDto targetFramework;
  private final String domainModelCode;
  private final String repositoryCode;
  private final String businessLogicCode;
  // Requisitos funcionales como descripción libre
  // Aquí iría algo como: "Paginación, joins, etc."
  private final String functionalRequirements;
  // Preferencias de salida
  // Formato estructural como: Clean Architecture, MVC, Repository Pattern, etc.
  private final String outputFormat;
  private final String targetSchema;
  private String usageExampleCode; // Opcional
  private final SupportedLanguageLargeModelsDto languageLargeModel;
}
