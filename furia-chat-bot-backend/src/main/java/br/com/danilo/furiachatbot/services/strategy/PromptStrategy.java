package br.com.danilo.furiachatbot.services.strategy;

import br.com.danilo.furiachatbot.enums.IntentType;

public interface PromptStrategy {

    String getPrompt(IntentType intentType);


    IntentType getIntentType(String intentType);

}
