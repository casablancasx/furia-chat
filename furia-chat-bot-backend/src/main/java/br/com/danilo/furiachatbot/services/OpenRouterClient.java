package br.com.danilo.furiachatbot.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class OpenRouterClient {

    private static final Logger log = LoggerFactory.getLogger(OpenRouterClient.class);

    @Qualifier("openRouterWebClient")
    private final WebClient openRouterWebClient;

    @Value("${openrouter.model}")
    private String openRouterModel;

    public OpenRouterClient(WebClient openRouterWebClient) {
        this.openRouterWebClient = openRouterWebClient;
    }

    public Mono<String> getCompletion(String prompt) {
        Map<String, Object> payload = Map.of(
                "model", openRouterModel,
                "messages", new Object[]{
                        Map.of("role", "user", "content", prompt)
                }
        );

        return openRouterWebClient.post()
                .uri("/chat/completions")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(responseBody -> {
                    JsonNode choices = responseBody.path("choices");
                    if (choices.isArray() && !choices.isEmpty()) {
                        JsonNode firstChoice = choices.get(0);
                        JsonNode message = firstChoice.path("message");
                        return message.path("content").asText("Não foi possível obter a resposta.");
                    }
                    return "Não foi possível extrair a resposta da IA.";
                })
                .doOnError(WebClientResponseException.class, error ->
                    log.error("Erro na requisição ao OpenRouter: Status={}, Corpo={}",
                            error.getStatusCode(), error.getResponseBodyAsString(), error)
                )
                .onErrorResume(e -> {
                    log.error("Falha ao obter completion do OpenRouter: {}", e.getMessage());
                    return Mono.just("Desculpe, não consegui me conectar com a IA no momento.");
                });
    }
}