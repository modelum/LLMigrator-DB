package com.umu.prompts.infrastructure.api.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Response DTO for Code Migration")
public class CodeMigrationResponseDto {

  @Schema(
      description = "Migrated domain and repository code",
      example = "public class UserRepository { ... }")
  private final String migratedDomainAndRepositoryCode;

  @Schema(
      description = "Explanation of the migrated domain and repository code",
      example =
          "This code represents the migrated UserRepository class with all necessary methods.")
  private final String migratedDomainAndRepositoryCodeExplication;

  @Schema(
      description = "Migrated service and queries code",
      example = "public class UserService { ... }")
  private final String migratedServiceAndQueriesCode;

  @Schema(
      description = "Explanation of the migrated service and queries code",
      example =
          "This code represents the migrated UserService class with all necessary methods and queries.")
  private final String migratedServiceAndQueriesCodeExplication;
}
