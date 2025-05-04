package br.com.danilo.furiachatbot.services.strategy.impl;

import br.com.danilo.furiachatbot.enums.IntentType;
import br.com.danilo.furiachatbot.services.strategy.PromptStrategy;
import org.springframework.stereotype.Component;

@Component
public class LastNewsPromptStrategy implements PromptStrategy {

    @Override
    public String getPrompt(IntentType intentType) {
        return "aqui estao as ultimas noticias do nosso time de CS2 FURIA";
    }

    @Override
    public IntentType getIntentType(String intentType) {
        return IntentType.LAST_NEWS;
    }
}