package com.umu.prompts.infrastructure.external.openrouter.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.micrometer.observation.ObservationRegistry;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.model.openai.autoconfigure.OpenAiChatProperties;
import org.springframework.ai.model.openai.autoconfigure.OpenAiConnectionProperties;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

/*
 * Basado en la clase `MultiChat` del repositorio:
 * https://github.com/pacphi/spring-ai-openrouter-example
 * Licencia: Apache License, Version 2.0
 *
 * Cambios realizados:
 * - Reorganización del código para una integración más clara con Spring Boot (`@Configuration`).
 * - Añadido soporte explícito para gestión de memoria de conversación (`ChatMemory`).
 * - Modificadas las cabeceras para únicamente aceptar JSON.
 * - Uso de propiedades externas definidas en `OpenRouterAiChatProperties`.
 *
 * Adaptado por Fabián Sola Durán (2025) para el proyecto académico Migrations-API-OpenRouter.
 */

@Configuration
public class OpenRouterClientConfiguration {

  @Bean
  public Map<String, ChatClient> chatClients(
      OpenAiConnectionProperties openAiConnectionProperties,
      OpenAiChatProperties openAiChatProperties,
      WebClient.Builder webClientBuilder,
      RetryTemplate retryTemplate,
      OpenRouterAiChatProperties openRouterProperties) {

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

    RestClient.Builder restClientBuilder =
        RestClient.builder().defaultHeaders(h -> h.addAll(headers));

    String apiKey = openAiConnectionProperties.getApiKey();
    if (openAiConnectionProperties.getApiKey().equalsIgnoreCase("redundant")
        && openAiChatProperties.getApiKey() != null
        && !openAiChatProperties.getApiKey().isEmpty()
        && !openAiChatProperties.getApiKey().isBlank()) {
      apiKey = openAiChatProperties.getApiKey();
    }

    final String baseUrl =
        openAiChatProperties.getBaseUrl() != null
            ? openAiChatProperties.getBaseUrl()
            : openAiConnectionProperties.getBaseUrl();

    final OpenAiApi openAiApi =
        new OpenAiApi(
            baseUrl,
            new SimpleApiKey(apiKey),
            headers,
            "/v1/chat/completions",
            "/v1/embeddings",
            restClientBuilder,
            webClientBuilder,
            RetryUtils.DEFAULT_RESPONSE_ERROR_HANDLER);

    return openRouterProperties.getOptions().getModels().stream()
        .collect(
            Collectors.toMap(
                model -> model,
                model -> {
                  OpenAiChatOptions chatOptions =
                      OpenAiChatOptions.fromOptions(openAiChatProperties.getOptions());
                  chatOptions.setModel(model);
                  chatOptions.setStreamUsage(false);
                  OpenAiChatModel openAiChatModel =
                      new OpenAiChatModel(
                          openAiApi,
                          chatOptions,
                          DefaultToolCallingManager.builder()
                              .observationRegistry(ObservationRegistry.NOOP)
                              .build(),
                          retryTemplate,
                          ObservationRegistry.NOOP);
                  return ChatClient.builder(openAiChatModel)
                      .defaultAdvisors(
                          MessageChatMemoryAdvisor.builder(chatMemory(chatMemoryRepository()))
                              .build())
                      .build();
                }));
  }

  @Bean
  public ChatMemoryRepository chatMemoryRepository() {
    return new InMemoryChatMemoryRepository();
  }

  @Bean
  public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
    return MessageWindowChatMemory.builder()
        .maxMessages(20)
        .chatMemoryRepository(chatMemoryRepository)
        .build();
  }

  @Bean
  public RestClientCustomizer restClientCustomizer() {
    return restClientBuilder -> {
      SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
      factory.setConnectTimeout((int) Duration.ofMinutes(20).toMillis());
      factory.setReadTimeout((int) Duration.ofMinutes(20).toMillis());
      restClientBuilder.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
      restClientBuilder.requestFactory(factory);
    };
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.registerModule(new ParameterNamesModule());
    return objectMapper;
  }
}
