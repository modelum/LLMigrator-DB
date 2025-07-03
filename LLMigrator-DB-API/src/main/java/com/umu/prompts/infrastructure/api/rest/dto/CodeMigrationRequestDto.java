package com.umu.prompts.infrastructure.api.rest.dto;

import com.umu.prompts.infrastructure.api.rest.dto.enums.DatabaseTypeDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.FrameworkTypeDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.ProgrammingLanguageDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.SupportedLanguageLargeModelsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Request DTO for Code Migration")
public class CodeMigrationRequestDto {

  @NotNull(message = "sourceDatabase is required")
  @Schema(description = "Source database type", example = "SQLite")
  private final DatabaseTypeDto sourceDatabase;

  @NotNull(message = "sourceLanguage is required")
  @Schema(description = "Source programming language for code migration", example = "Java")
  private final ProgrammingLanguageDto sourceLanguage;

  @NotNull(message = "targetLanguage is required")
  @Schema(description = "Target programming language for code migration", example = "Kotlin")
  private final ProgrammingLanguageDto targetLanguage;

  @NotNull(message = "targetDatabase is required")
  @Schema(description = "Target database type", example = "PostgreSQL")
  private final DatabaseTypeDto targetDatabase;

  @NotNull(message = "sourceFramework is required")
  @Schema(description = "Source framework for code migration", example = "Spring Data JPA")
  private final FrameworkTypeDto sourceFramework;

  @NotNull(message = "targetFramework is required")
  @Schema(description = "Target framework for code migration", example = "MICRONAUT")
  private final FrameworkTypeDto targetFramework;

  @NotNull(message = "domainModelCode is required")
  @NotEmpty(message = "domainModelCode cannot be empty")
  @Schema(description = "Code of the domain model", example = "public class User { ... }")
  private final String domainModelCode;

  @NotNull(message = "repositoryCode is required")
  @NotEmpty(message = "repositoryCode cannot be empty")
  @Schema(
      description = "Code of the repository",
      example = "public interface UserRepository extends JpaRepository<User, Long> { ... }")
  private final String repositoryCode;

  @NotNull(message = "businessLogicCode is required")
  @NotEmpty(message = "businessLogicCode cannot be empty")
  @Schema(
      description = "business Logic Code. Example: Services code.",
      example = "public class UserService { ... }")
  private final String businessLogicCode;

  @NotNull(message = "functionalRequirements is required")
  @NotEmpty(message = "functionalRequirements cannot be empty")
  @Schema(
      description = "Description of functional requirements",
      example = "Pagination, joins, etc.")
  private final String functionalRequirements;

  @NotNull(message = "outputFormat is required")
  @NotEmpty(message = "outputFormat cannot be empty")
  @Schema(
      description = "Output format preferences",
      example = "Clean Architecture, MVC, Repository Pattern")
  private final String outputFormat;

  @NotNull(message = "targetSchema is required")
  @NotEmpty(message = "targetSchema cannot be empty")
  @Schema(
      description = "Target schema from the target database for the migration",
      example = "CREATE TABLE users (id SERIAL PRIMARY KEY, name VARCHAR(100), email VARCHAR(100))")
  private final String targetSchema;

  @Schema(
      description = "Example usage code",
      example = "public static void main(String[] args) { ... }")
  private String usageExampleCode;

  @NotNull(message = "languageLargeModel is required")
  @Schema(description = "Language large model for code migration", example = "openai/gpt-4o-mini")
  private final SupportedLanguageLargeModelsDto languageLargeModel;
}
