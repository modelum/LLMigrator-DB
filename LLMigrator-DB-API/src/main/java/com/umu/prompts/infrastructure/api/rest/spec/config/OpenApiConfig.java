package com.umu.prompts.infrastructure.api.rest.spec.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Servicio de migración de bases de datos - LLMigrator-DB")
                .description(" Gestión de migraciones de bases de datos")
                .version("1.0.0"));
  }
}
