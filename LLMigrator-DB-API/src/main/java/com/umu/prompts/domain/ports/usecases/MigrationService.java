package com.umu.prompts.domain.ports.usecases;


import com.umu.prompts.domain.model.CodeMigrationRequest;
import com.umu.prompts.domain.model.CodeMigrationResponse;
import com.umu.prompts.domain.model.MigrationDbRequest;
import com.umu.prompts.domain.model.MigrationDbResponse;

public interface MigrationService {
  MigrationDbResponse migrateDatabase(MigrationDbRequest infoForMigration);
  CodeMigrationResponse migrateDatabaseCode(CodeMigrationRequest infoForCodeMigration);
}
