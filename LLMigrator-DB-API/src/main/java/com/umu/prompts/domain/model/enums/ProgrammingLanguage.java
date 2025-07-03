package com.umu.prompts.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgrammingLanguage {

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

    public static ProgrammingLanguage fromValue(String value) {
        for (ProgrammingLanguage language : ProgrammingLanguage.values()) {
            if (language.displayName.equals(value)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
