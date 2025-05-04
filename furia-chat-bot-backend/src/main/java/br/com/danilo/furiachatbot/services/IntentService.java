package br.com.danilo.furiachatbot.services;


import br.com.danilo.furiachatbot.model.IntentTypeResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class IntentService {


    @Qualifier("intentDetectionWebClient")
    private final WebClient intentDetectionWebClient;


    public IntentService(WebClient intentDetectionWebClient) {
        this.intentDetectionWebClient = intentDetectionWebClient;
    }

    public Mono<IntentTypeResponse> detectIntent(String userMessage) {
        String msg = userMessage.toLowerCase();
        Map<String, String> payload = Map.of("text", msg);

        return intentDetectionWebClient.post()
                .uri("/intent")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(IntentTypeResponse.class);
    }
}
