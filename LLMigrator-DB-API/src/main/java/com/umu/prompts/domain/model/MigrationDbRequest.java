package com.umu.prompts.domain.model;

import com.umu.prompts.domain.model.enums.DatabaseType;
import com.umu.prompts.domain.model.enums.SupportedLanguageLargeModels;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MigrationDbRequest {
  private final DatabaseType sourceDatabase;

  private final DatabaseType targetDatabase;

  private final String accessRequirements;

  private final String databaseSchema;

  private String databaseDocuments;

  private final String migrationRequirements;

  private final SupportedLanguageLargeModels languageLargeModel;
}
