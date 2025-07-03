package com.umu.prompts.domain.ports.external;

import com.umu.prompts.domain.model.CodeMigrationRequest;
import com.umu.prompts.domain.model.MigrationDbRequest;
import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateCodeDatabaseResponseDto;
import com.umu.prompts.infrastructure.external.openrouter.dto.OpenrouterMigrateDatabaseResponseDto;

public interface LanguageLargeModelPort {
  public OpenrouterMigrateDatabaseResponseDto migrateDatabase(
      MigrationDbRequest request);

  public OpenrouterMigrateCodeDatabaseResponseDto migrateDatabaseCode(
      CodeMigrationRequest request);
}
