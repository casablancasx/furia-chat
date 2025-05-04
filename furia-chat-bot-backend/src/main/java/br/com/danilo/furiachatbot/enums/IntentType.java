package br.com.danilo.furiachatbot.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de intenção que podem ser identificadas na mensagem do usuário.")
public enum IntentType {
    @Schema(description = "Informações gerais sobre o time.")
    TEAM,
    @Schema(description = "Informações sobre os jogadores do time.")
    PLAYERS,
    @Schema(description = "Informações sobre o técnico (coach).")
    COACH,
    @Schema(description = "Informações sobre partidas anteriores.")
    PREVIOUS_MATCH,
    @Schema(description = "Informações sobre próximas partidas.")
    UPCOMING_MATCH,
    @Schema(description = "Informações sobre transferências de jogadores.")
    TRANSFERS,
    @Schema(description = "Informações sobre campeonatos atuais ou futuros.")
    CHAMPIONSHIP,
    @Schema(description = "Intenção geral ou não específica.")
    GENERAL,
    @Schema(description = "Últimas notícias sobre o time.")
    LAST_NEWS,
    @Schema(description = "Informações sobre troféus conquistados.")
    TROPHIES,
    @Schema(description = "A pergunta está fora do domínio de conhecimento do bot (FURIA CS2).")
    OUT_OF_DOMAIN
}