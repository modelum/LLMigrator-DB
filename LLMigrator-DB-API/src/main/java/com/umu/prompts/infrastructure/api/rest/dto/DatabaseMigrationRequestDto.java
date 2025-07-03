package com.umu.prompts.infrastructure.api.rest.dto;

import com.umu.prompts.infrastructure.api.rest.dto.enums.DatabaseTypeDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.SupportedLanguageLargeModelsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@Schema(description = "Request DTO for Database Migration")
public class DatabaseMigrationRequestDto {

  @NotNull(message = "Source database type cannot be null")
  @Schema(description = "Source database type", example = "SQLite")
  private final DatabaseTypeDto sourceDatabaseType;

  @NotNull(message = "Target database type cannot be null")
  @Schema(description = "Target database type", example = "PostgreSQL")
  private final DatabaseTypeDto targetDatabaseType;

  @NotNull(message = "Access requirements cannot be null")
  @NotEmpty(message = "Access requirements cannot be empty")
  @Schema(
      description = "Access requirements for the migration",
      example = "Administrator privileges required")
  private final String accessRequirements;

  @NotNull(message = "Database schema cannot be null")
  @NotEmpty(message = "Database schema cannot be empty")
  @Schema(description = "Definition of the database schema", example = "CREATE TABLE ...")
  private final String databaseSchema;

  @Schema(
      description = "Optional database documentation details",
      example = "Documentation about the database structure")
  private String databaseDocuments;

  @NotNull(message = "Migration requirements cannot be null")
  @NotEmpty(message = "Migration requirements cannot be empty")
  @Schema(
      description = "Requirements for performing the migration",
      example = "Backup the database before migrating")
  private final String migrationRequirements;

  @NotNull(message = "languageLargeModel is required")
  @Schema(description = "Language large model for code migration", example = "openai/gpt-4o-mini")
  private final SupportedLanguageLargeModelsDto languageLargeModel;
}
