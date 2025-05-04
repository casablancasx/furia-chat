package br.com.danilo.furiachatbot.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa a mensagem enviada pelo usuário para o chat bot.")
public record MessageRequest(
        @Schema(description = "O texto da mensagem do usuário.", example = "Quais os próximos jogos da Furia?")
        String userMessage
) {}