package com.umu.prompts.infrastructure.api.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Response DTO for Database Migration")
public class DatabaseMigrationResponseDto {

  @Schema(description = "Script for migrating the database schema", example = "ALTER TABLE ...")
  private final String scriptForMigrateSchema;

  @Schema(description = "Metadata for schema migration", example = "Additional schema information")
  private final String metadataForMigrateSchema;

  @Schema(
      description = "Guidelines for validating the database schema",
      example = "Steps for schema validation")
  private final String guidelinesForValidateSchema;

  @Schema(description = "Script for migrating the data", example = "INSERT INTO ...")
  private final String scriptForDataMigration;

  @Schema(
      description = "Metadata for data migration",
      example = "Additional data migration details")
  private final String metadataForDataMigration;

  @Schema(description = "Script for data validation", example = "SELECT COUNT(*) ...")
  private final String scriptForDataValidation;

  @Schema(
      description = "Metadata for data validation",
      example = "Additional data validation details")
  private final String metadataForDataValidation;
}
