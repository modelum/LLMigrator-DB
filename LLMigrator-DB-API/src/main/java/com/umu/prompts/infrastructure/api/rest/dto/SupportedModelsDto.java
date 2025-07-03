package com.umu.prompts.infrastructure.api.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public final class SupportedModelsDto {
    private final List<String> supportedModels;
}
