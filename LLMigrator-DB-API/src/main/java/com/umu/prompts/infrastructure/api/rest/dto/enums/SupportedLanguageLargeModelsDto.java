package com.umu.prompts.infrastructure.api.rest.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Schema(description = "Enumeration for Large Language Models supported by the tool LLMigrate")
public enum SupportedLanguageLargeModelsDto {
  @Schema(description = "DeepSeek R1 model")
  DEEPSEEK_R1("deepseek/deepseek-r1-0528:free"),

  @Schema(description = "Gemini Flash model")
  GEMINI_FLASH("google/gemini-2.0-flash-001"),

  @Schema(description = "GPT-4o Mini model")
  GPT_4O_MINI("openai/gpt-4o-mini");

  private final String modelId;

  @Override
  public String toString() {
    return modelId;
  }

  @JsonCreator
  public static SupportedLanguageLargeModelsDto fromValue(String value) {
    return fromModelId(value);
  }

  private static SupportedLanguageLargeModelsDto fromModelId(String modelId) {
    for (SupportedLanguageLargeModelsDto model : values()) {
      if (model.modelId.equalsIgnoreCase(modelId)) {
        return model;
      }
    }
    throw new IllegalArgumentException("Modelo no soportado: " + modelId);
  }
}
