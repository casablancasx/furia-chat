package br.com.danilo.furiachatbot.model;

import br.com.danilo.furiachatbot.enums.IntentType;

public record IntentTypeResponse(
        IntentType intent,
        Double score
) {
}
