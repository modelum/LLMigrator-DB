package com.umu.prompts.infrastructure.external.openrouter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenrouterMigrateCodeDatabaseResponseDto {
  private final String migratedDomainAndRepositoryCode;
  private final String migratedDomainAndRepositoryCodeExplication;
  private final String migratedServiceAndQueriesCode;
  private final String migratedServiceAndQueriesCodeExplication;
}
