package com.umu.prompts.infrastructure.api.rest.spec;

import com.umu.prompts.infrastructure.api.rest.dto.*;
import com.umu.prompts.infrastructure.api.rest.dto.error.ProblemDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MigrationApi {
  @Operation(
      operationId = "migrateDatabase",
      summary = "Migrar Base de Datos",
      description =
          "Realiza la migración de la base de datos. Devuelve un objeto DatabaseMigrationResponseDto en caso de éxito.",
      tags = {"Database-Migration"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operación exitosa",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DatabaseMigrationResponseDto.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Petición errónea",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno en el servidor",
            content = @Content)
      })
  @PostMapping("/api/v1/migrations")
  ResponseEntity<DatabaseMigrationResponseDto> migrateDatabase(
      @Parameter(
              name = "databaseMigrationRequestDto",
              description = "Objeto de solicitud para la migración de la base de datos",
              required = true,
              schema = @Schema(implementation = DatabaseMigrationRequestDto.class))
          @Valid
          @RequestBody
          DatabaseMigrationRequestDto requestDto)
      throws Exception;

  @Operation(
      operationId = "migrateCode",
      summary = "Migrar Código",
      description =
          "Realiza la migración del código. Devuelve un objeto CodeMigrationResponseDto en caso de éxito.",
      tags = {"Code-Migration"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operación exitosa",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CodeMigrationResponseDto.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Petición errónea",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno en el servidor",
            content = @Content)
      })
  @PostMapping("/api/v1/code-migrations")
  public ResponseEntity<CodeMigrationResponseDto> migrateCode(
      @Parameter(
              name = "codeMigrationRequestDto",
              description = "Objeto de solicitud para la migración de código",
              required = true,
              schema = @Schema(implementation = CodeMigrationRequestDto.class))
          @Valid
          @RequestBody
          CodeMigrationRequestDto codeMigrationRequestDto)
      throws Exception;

  @Operation(
      operationId = "getSupportedLargeModels",
      summary = "Obtener Modelos Grandes Soportados",
      description = "Devuelve una lista de modelos grandes soportados para migraciones de código.",
      tags = {"Large-Models"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operación exitosa",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SupportedModelsDto.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno en el servidor",
            content = @Content)
      })
  @GetMapping("/api/v1/migrations/largeModels")
  public ResponseEntity<SupportedModelsDto> getSupportedLargeModels() throws Exception;
}
