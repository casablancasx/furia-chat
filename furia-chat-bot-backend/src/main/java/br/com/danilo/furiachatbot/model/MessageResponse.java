package br.com.danilo.furiachatbot.model;


import br.com.danilo.furiachatbot.enums.IntentType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa a resposta do chat bot para o usuário.")
public record MessageResponse(
        @Schema(description = "A intenção identificada na mensagem do usuário.", implementation = IntentType.class, example = "UPCOMING_MATCH")
        IntentType intent,
        @Schema(description = "O texto da resposta gerada pelo bot.", example = "A FURIA jogará contra a NAVI amanhã às 15:00.")
        String botResponse,
        @Schema(description = "O contexto de dados utilizado pela IA para gerar a resposta (pode ser um objeto ou lista, dependendo da intenção).", nullable = true, example = "[{\"team1_logo\": \"url_logo_furia\", \"team1_name\": \"FURIA\", ...}]")
        Object context
) {
}