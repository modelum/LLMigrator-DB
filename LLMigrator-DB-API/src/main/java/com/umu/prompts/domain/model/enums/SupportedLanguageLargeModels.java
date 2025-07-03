package com.umu.prompts.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SupportedLanguageLargeModels {
  DEEPSEEK_R1("deepseek/deepseek-r1-0528:free"), // <-- modelo añadido
  GEMINI_FLASH("google/gemini-2.0-flash-001"),
  GPT_4O_MINI("openai/gpt-4o-mini");

  private final String modelId;

  public String getModelId() {
    return modelId;
  }

  public static SupportedLanguageLargeModels fromModelId(String modelId) {
    for (SupportedLanguageLargeModels model : values()) {
      if (model.modelId.equalsIgnoreCase(modelId)) {
        return model;
      }
    }
    throw new IllegalArgumentException("Modelo no soportado: " + modelId);
  }
}
