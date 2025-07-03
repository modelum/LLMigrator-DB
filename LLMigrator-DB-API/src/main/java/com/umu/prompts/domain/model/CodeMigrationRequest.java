package com.umu.prompts.domain.model;

import com.umu.prompts.domain.model.enums.DatabaseType;
import com.umu.prompts.domain.model.enums.FrameworkType;
import com.umu.prompts.domain.model.enums.ProgrammingLanguage;
import com.umu.prompts.domain.model.enums.SupportedLanguageLargeModels;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CodeMigrationRequest {
  private final DatabaseType sourceDatabase;
  private final DatabaseType targetDatabase;
  private final ProgrammingLanguage sourceLanguage;
  private final ProgrammingLanguage targetLanguage;
  private final FrameworkType sourceFramework;
  private final FrameworkType targetFramework;
  private final String domainModelCode;
  private final String repositoryCode;
  private final String businessLogicCode;
  // Requisitos funcionales como descripción libre
  // Aquí iría algo como: "Paginación, joins, etc."
  private final String functionalRequirements;
  // Preferencias de salida
  // Formato estructural como: Clean Architecture, MVC, Repository Pattern, etc.
  private final String targetSchema;
  private final String outputFormat;
  private String usageExampleCode; // Opcional
  private final SupportedLanguageLargeModels languageLargeModel;
}
