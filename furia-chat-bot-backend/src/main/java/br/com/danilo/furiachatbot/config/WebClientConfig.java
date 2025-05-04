package br.com.danilo.furiachatbot.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {


    @Value("${openrouter.key}")
    private String openRouterApiKey;


    @Bean("intentDetectionWebClient")
    public WebClient intentDetectionWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://intent-service:8001") 
                .build();
    }

    @Bean("furiaScrapingClient")
    public WebClient furiaScrapingClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://scraping-service:8000") 
                .build();
    }

    @Bean("openRouterWebClient")
    public WebClient openRouterWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://openrouter.ai/api/v1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json") // Usar constante
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openRouterApiKey)
                .build();
    }

}