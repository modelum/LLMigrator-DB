package com.umu.prompts.infrastructure.api.rest.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgrammingLanguageDto {
  JAVA("Java"),
  DOTNET("DOTNET"),
  JAVASCRIPT("JavaScript"),
  TYPESCRIPT("TypeScript"),
  PYTHON("Python"),
  RUBY("Ruby"),
  GO("Go"),
  PHP("PHP"),
  OTROS("Otros");

  private final String displayName;

  @Override
  public String toString() {
    return displayName;
  }

  @JsonCreator
  public static ProgrammingLanguageDto fromValue(String value) {
    for (ProgrammingLanguageDto language : ProgrammingLanguageDto.values()) {
      if (language.displayName.equals(value)) {
        return language;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
