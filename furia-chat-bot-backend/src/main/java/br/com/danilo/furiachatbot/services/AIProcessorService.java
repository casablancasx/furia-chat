package br.com.danilo.furiachatbot.services;

import br.com.danilo.furiachatbot.enums.IntentType;
import br.com.danilo.furiachatbot.services.strategy.PromptStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIProcessorService {

    private final OpenRouterClient openRouterClient;
    private final ObjectMapper objectMapper;
    private final Map<IntentType, PromptStrategy> strategyMap;

    public Mono<String> generateResponse(String userMessage, Object contextData, IntentType intentType) {
        try {

            if (intentType.equals(IntentType.LAST_NEWS)){
                return Mono.just("Aqui estão as últimas notícias sobre nosso time de CS2:");

            }

            String contextJson = objectMapper.writeValueAsString(contextData);
            PromptStrategy strategy = strategyMap.get(intentType);

            if (strategy == null) {
                log.warn("Nenhuma PromptStrategy encontrada para o tipo: {}", intentType);
                throw new IllegalStateException("Nenhuma PromptStrategy encontrada para o tipo: " + intentType);
            }

            String prompt = strategy.getPrompt(intentType)
                    + "\n\ncontext= ```" + contextJson + "```\n\nPergunta: '" + userMessage + "'";

            return openRouterClient.getCompletion(prompt);

        } catch (JsonProcessingException e) {
            log.error("Erro ao processar dados JSON: {}", e.getMessage());
            return Mono.error(new RuntimeException("Erro ao processar dados para IA", e));
        }
    }
}
