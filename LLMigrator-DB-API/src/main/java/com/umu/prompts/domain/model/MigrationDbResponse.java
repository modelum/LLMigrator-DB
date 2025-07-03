package com.umu.prompts.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MigrationDbResponse {
  private final String scriptForMigrateSchema;

  private final String metadataForMigrateSchema;

  private final String guidelinesForValidateSchema;

  private final String scriptForDataMigration;

  private final String metadataForDataMigration;

  private final String scriptForDataValidation;

  private final String metadataForDataValidation;
}
