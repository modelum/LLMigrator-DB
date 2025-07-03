package com.umu.prompts.infrastructure.external.openrouter.dto;

import com.umu.prompts.infrastructure.api.rest.dto.enums.DatabaseTypeDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.SupportedLanguageLargeModelsDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenrouterMigrateDatabaseRequestDto {
    private final DatabaseTypeDto sourceDatabaseType;

    private final DatabaseTypeDto targetDatabaseType;

    private final String accessRequirements;

    private final String databaseSchema;

    private String databaseDocuments;

    private final String migrationRequirements;

    private final SupportedLanguageLargeModelsDto languageLargeModel;
}
