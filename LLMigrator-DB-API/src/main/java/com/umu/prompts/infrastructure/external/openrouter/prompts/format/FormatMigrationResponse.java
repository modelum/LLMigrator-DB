package com.umu.prompts.infrastructure.external.openrouter.prompts.format;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FormatMigrationResponse(String script, String scriptExplication) {}
