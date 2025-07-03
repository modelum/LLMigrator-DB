package com.umu.prompts.infrastructure.external.openrouter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenrouterMigrateDatabaseResponseDto {
  private final String scriptForMigrateSchema;

  private final String metadataForMigrateSchema;

  private final String guidelinesForValidateSchema;

  private final String scriptForDataMigration;

  private final String metadataForDataMigration;

  private final String scriptForDataValidation;

  private final String metadataForDataValidation;
}
