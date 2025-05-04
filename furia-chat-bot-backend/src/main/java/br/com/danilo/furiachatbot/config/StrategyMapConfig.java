package br.com.danilo.furiachatbot.config;

import br.com.danilo.furiachatbot.enums.IntentType;
import br.com.danilo.furiachatbot.services.strategy.PromptStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
public class StrategyMapConfig {

    @Bean
    public Map<IntentType, PromptStrategy> strategyMap(List<PromptStrategy> strategies) {
        Map<IntentType, PromptStrategy> map = new EnumMap<>(IntentType.class);
        for (PromptStrategy strategy : strategies) {
            IntentType type = strategy.getIntentType(strategy.getClass().getSimpleName());
            map.put(type, strategy);
        }
        return map;
    }
}
