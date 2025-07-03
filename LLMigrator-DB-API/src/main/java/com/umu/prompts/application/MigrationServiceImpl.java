package com.umu.prompts.application;

import com.umu.prompts.domain.model.CodeMigrationRequest;
import com.umu.prompts.domain.model.CodeMigrationResponse;
import com.umu.prompts.domain.model.MigrationDbRequest;
import com.umu.prompts.domain.model.MigrationDbResponse;
import com.umu.prompts.domain.ports.external.LanguageLargeModelPort;
import com.umu.prompts.domain.ports.usecases.MigrationService;
import com.umu.prompts.infrastructure.external.openrouter.mapper.OpenrouterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MigrationServiceImpl implements MigrationService {

  private final LanguageLargeModelPort languageLargeModelPort;

  @Override
  public MigrationDbResponse migrateDatabase(MigrationDbRequest infoForMigration) {
    return OpenrouterMapper.toDomainModel(languageLargeModelPort.migrateDatabase(infoForMigration));
  }

  @Override
  public CodeMigrationResponse migrateDatabaseCode(CodeMigrationRequest infoForCodeMigration) {
    return OpenrouterMapper.toDomainModel(
        this.languageLargeModelPort.migrateDatabaseCode(infoForCodeMigration));
  }
}
