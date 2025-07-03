package com.umu.prompts.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodeMigrationResponse {
  private final String migratedDomainAndRepositoryCode;
  private final String migratedDomainAndRepositoryCodeExplication;
  private final String migratedServiceAndQueriesCode;
  private final String migratedServiceAndQueriesCodeExplication;
}
