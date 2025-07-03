package com.umu.prompts.infrastructure.external.openrouter.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/*
 * Basado en el diseño de propiedades del repositorio:
 * https://github.com/pacphi/spring-ai-openrouter-example
 * Licencia: Apache License, Version 2.0
 *
 * Adaptado por Fabián Sola Durán (2025) para su uso con Spring Boot con anotaciones Lombok.
 */


@Setter
@Getter
public class Options {
  private Set<String> models;
}
