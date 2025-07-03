package com.umu.prompts.infrastructure.api.rest.controller;

import com.umu.prompts.domain.ports.usecases.MigrationService;
import com.umu.prompts.infrastructure.api.rest.dto.*;
import com.umu.prompts.infrastructure.api.rest.dto.enums.SupportedLanguageLargeModelsDto;
import com.umu.prompts.infrastructure.api.rest.dto.validators.CodeMigrationRequestDtoValidator;
import com.umu.prompts.infrastructure.api.rest.mapper.ControllerMapper;
import com.umu.prompts.infrastructure.api.rest.spec.MigrationApi;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MigrationController implements MigrationApi {

  private final MigrationService migrationService;
  private final CodeMigrationRequestDtoValidator codeMigrationRequestDtoValidator;

  @InitBinder("codeMigrationRequestDto")
  public void initBinder(WebDataBinder binder) {
    binder.addValidators(codeMigrationRequestDtoValidator);
  }

  @Override
  @PostMapping("/api/v1/migrations")
  public ResponseEntity<DatabaseMigrationResponseDto> migrateDatabase(
      @Valid @RequestBody DatabaseMigrationRequestDto databaseMigrationRequestDto)
      throws Exception {
    return ResponseEntity.ok(
        ControllerMapper.toDto(
            migrationService.migrateDatabase(
                ControllerMapper.fromDto(databaseMigrationRequestDto))));
  }

  @Override
  @PostMapping("/api/v1/code-migrations")
  public ResponseEntity<CodeMigrationResponseDto> migrateCode(
      @Valid @RequestBody CodeMigrationRequestDto codeMigrationRequestDto) throws Exception {
    return ResponseEntity.ok(
        ControllerMapper.toDto(
            migrationService.migrateDatabaseCode(
                ControllerMapper.fromDto(codeMigrationRequestDto))));
  }

  @Override
  @GetMapping("/api/v1/supported-language-large-models")
  public ResponseEntity<SupportedModelsDto> getSupportedLargeModels() throws Exception {
    List<String> models =
        Arrays.stream(SupportedLanguageLargeModelsDto.values())
            .map(SupportedLanguageLargeModelsDto::getModelId)
            .toList();
    return ResponseEntity.ok(new SupportedModelsDto(models));
  }
}
