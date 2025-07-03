package com.umu.prompts.infrastructure.external.openrouter.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.umu.prompts.infrastructure.external.openrouter.prompts.format.FormatMigrationResponse;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SafeJsonParser {

  private final ObjectMapper objectMapper;

  public FormatMigrationResponse parse(String rawResponse) {

    String cleaned = rawResponse.replaceAll("^```(?:json)?\\s*", "").replaceAll("\\s*```$", "").trim();

    String jsonBlock = findLikelyJsonContainingKeys(cleaned, "script", "scriptExplication");

    jsonBlock = attemptSafeJsonClosureFix(jsonBlock);

    try {
      return objectMapper.readValue(jsonBlock, FormatMigrationResponse.class);
    } catch (Exception firstEx) {
      try {
        JsonNode node = objectMapper.readTree(jsonBlock);
        JsonNode fixed = fixInvalidEscapes(node);
        return objectMapper.treeToValue(fixed, FormatMigrationResponse.class);
      } catch (Exception secondEx) {
        String preview = rawResponse.length() > 300 ? rawResponse.substring(0, 300) + "..." : rawResponse;
        throw new RuntimeException(
            "Error al parsear la respuesta tras intento de corrección. Preview:\n"
                + preview
                + "\nMensaje: "
                + secondEx.getMessage(),
            secondEx);
      }
    }
  }

  /** Encuentra un bloque JSON que contenga las claves esperadas. */
  private String findLikelyJsonContainingKeys(String input, String... expectedKeys) {
    int start = 0;
    while ((start = input.indexOf('{', start)) != -1) {
      int end = findMatchingClosingBrace(input, start);
      if (end != -1) {
        String candidate = input.substring(start, end + 1);
        if (containsAllExpectedKeys(candidate, expectedKeys)) {
          return candidate;
        }
      }
      start++;
    }

    return input;
  }

  /** Verifica si el candidato contiene todas las claves esperadas. */
  private boolean containsAllExpectedKeys(String candidate, String... expectedKeys) {
    return Arrays.stream(expectedKeys)
        .allMatch(key -> candidate.contains("\"" + key + "\""));
  }

  /** Encuentra el cierre que corresponde a la llave de apertura. */
  private int findMatchingClosingBrace(String input, int openPos) {
    int depth = 0;
    boolean inString = false;
    for (int i = openPos; i < input.length(); i++) {
      char c = input.charAt(i);
      if (c == '"' && (i == 0 || input.charAt(i - 1) != '\\')) {
        inString = !inString;
      } else if (!inString) {
        if (c == '{')
          depth++;
        else if (c == '}') {
          depth--;
          if (depth == 0)
            return i;
        }
      }
    }
    return -1;
  }

  /**
   * Añade las llaves de cierre necesarias sin contar las que están dentro de
   * strings.
   */
  private String attemptSafeJsonClosureFix(String json) {
    int open = 0;
    boolean inString = false;
    for (int i = 0; i < json.length(); i++) {
      char c = json.charAt(i);
      if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
        inString = !inString;
      } else if (!inString) {
        if (c == '{')
          open++;
        else if (c == '}')
          open--;
      }
    }
    if (open > 0) {
      StringBuilder sb = new StringBuilder(json);
      for (int i = 0; i < open; i++) {
        sb.append('}');
      }
      return sb.toString();
    }
    return json;
  }

  /** Corrige strings con secuencias de escape inválidas. */
  private JsonNode fixInvalidEscapes(JsonNode node) {
    if (node.isObject()) {
      ObjectNode fixed = ((ObjectNode) node).deepCopy();
      node.fieldNames()
          .forEachRemaining(
              field -> {
                JsonNode value = node.get(field);
                fixed.set(field, fixInvalidEscapes(value));
              });
      return fixed;
    } else if (node.isArray()) {
      ArrayNode arr = objectMapper.createArrayNode();
      for (JsonNode item : node) {
        arr.add(fixInvalidEscapes(item));
      }
      return arr;
    } else if (node.isTextual()) {
      String text = node.textValue();
      String fixed = text.replaceAll("\\\\([^\"\\\\/bfnrtu])", "\\\\\\\\$1");
      fixed = fixed.replaceAll("(?<!\\\\)\"", "\\\\\"");
      return new TextNode(fixed);
    } else {
      return node;
    }
  }
}
