package br.com.danilo.furiachatbot.services;

import br.com.danilo.furiachatbot.enums.IntentType;
import br.com.danilo.furiachatbot.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class ChatService {

    @Qualifier("furiaScrapingClient")
    private final WebClient furiaScrapingClient;
    private final OpenRouterClient openRouterClient;
    private final IntentService intentService;
    private final AIProcessorService aiProcessorService;

    public ChatService(WebClient furiaScrapingClient,
                       OpenRouterClient openRouterClient,
                       IntentService intentService,
                       AIProcessorService aiProcessorService) {
        this.furiaScrapingClient = furiaScrapingClient;
        this.openRouterClient = openRouterClient;
        this.intentService = intentService;
        this.aiProcessorService = aiProcessorService;
    }

    public Mono<MessageResponse> getBotResponse(String userMessage) {
        return intentService.detectIntent(userMessage).flatMap(intentResponse -> {
            IntentType intentType = intentResponse.intent();
            return switch (intentType) {
                case CHAMPIONSHIP -> fetchListAndCreateResponse(userMessage, "/championships/active", new ParameterizedTypeReference<List<ChampionshipDTO>>() {}, intentResponse);
                case TROPHIES, PLAYERS, COACH, TEAM -> fetchAndCreateResponse(userMessage, "/team", InfoTeamDTO.class, intentResponse);
                case UPCOMING_MATCH -> fetchListAndCreateResponse(userMessage, "/matches/upcoming", new ParameterizedTypeReference<List<UpcomingMatchDTO>>() {}, intentResponse);
                case PREVIOUS_MATCH -> fetchListAndCreateResponse(userMessage, "/matches/previous", new ParameterizedTypeReference<List<PreviousMatchDTO>>() {}, intentResponse);
                case TRANSFERS -> fetchListAndCreateResponse(userMessage, "/transfers", new ParameterizedTypeReference<List<TransferDTO>>() {}, intentResponse);
                case LAST_NEWS -> fetchListAndCreateResponse(userMessage, "/news/latest", new ParameterizedTypeReference<List<NewsDTO>>() {}, intentResponse);
                case OUT_OF_DOMAIN -> {
                    String outOfDomainMsg = "Desculpe, só posso responder perguntas sobre o time de CS da FURIA.";
                    yield Mono.just(new MessageResponse(intentResponse.intent(), outOfDomainMsg, null));
                }
                default -> {
                    String constrainedPrompt = "Você é um assistente especializado sobre o time de e-sports FURIA CS2...";
                    yield openRouterClient.getCompletion(constrainedPrompt).map(botReply -> new MessageResponse(intentResponse.intent(), botReply, null));
                }
            };
        });
    }

    private <T> Mono<MessageResponse> fetchAndCreateResponse(String userMessage, String uri, Class<T> dtoClass, IntentTypeResponse intentResponse) {
        return furiaScrapingClient.get().uri(uri).retrieve()
                .bodyToMono(dtoClass)
                .flatMap(contextData -> aiProcessorService.generateResponse(userMessage, contextData, intentResponse.intent())
                        .map(botReply -> new MessageResponse(intentResponse.intent(), botReply, contextData)))
                .onErrorResume(e -> {
                    log.error("Erro ao buscar ou processar dados para URI {}: {}", uri, e.getMessage());
                    return Mono.just(new MessageResponse(intentResponse.intent(), "Desculpe, tive um problema ao buscar as informações.", null));
                });
    }

    private <T> Mono<MessageResponse> fetchListAndCreateResponse(String userMessage, String uri, ParameterizedTypeReference<List<T>> typeRef, IntentTypeResponse intentResponse) {
        return furiaScrapingClient.get().uri(uri).retrieve()
                .bodyToMono(typeRef)
                .flatMap(contextData -> aiProcessorService.generateResponse(userMessage, contextData, intentResponse.intent())
                        .map(botReply -> new MessageResponse(intentResponse.intent(), botReply, contextData)))
                .onErrorResume(e -> {
                    log.error("Erro ao buscar ou processar dados da lista para URI {}: {}", uri, e.getMessage());
                    return Mono.just(new MessageResponse(intentResponse.intent(), "Desculpe, tive um problema ao buscar as informações.", null));
                });
    }
}
