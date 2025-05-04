package br.com.danilo.furiachatbot.services.strategy.impl;

import br.com.danilo.furiachatbot.enums.IntentType;
import br.com.danilo.furiachatbot.services.strategy.PromptStrategy;
import org.springframework.stereotype.Component;

@Component
public class PlayerPromptStrategy implements PromptStrategy {

    @Override
    public String getPrompt(IntentType intentType) {
        return """
                
                Com base no contexto acima JSON fornecido, você deve responder perguntas sobre o time da FURIA.
                
                Você é um assistente especializado em eSports, com foco em Counter-Strike.
      
                Com base nas informações fornecidas sobre o time da FURIA, responda às perguntas de forma natural e concisa.
            
                Exemplo de Pergunta do Usuário:
                
                Quem são os jogadores do time da FURIA?
                
                Resposta Concisa: Listar apenas os nomes dos jogadores titulares e, se aplicável, mencionar os jogadores no banco de reservas.
                
                Estilo Natural: Utilizar uma linguagem fluida e natural, como:
                
                Os jogadores titulares da FURIA são yuurih, KSCERATO, FalleN, molodoy e YEKINDAR. No banco de reservas estão chelo e skullz.
                
                Detalhes Sob Demanda: Fornecer informações adicionais, como estatísticas ou histórico dos jogadores, apenas se o usuário solicitar especificamente.
                 System:
                ### Dicas adicionais
                
                1. **“System” vs. “User”**: Use a **mensagem de sistema** para dar regras de formatação rígidas (ex.: “Não imprima o JSON”).
                2. **Stop sequences**: Defina uma sequência que interrompa a geração assim que terminar a resposta (“```” ou uma string vazia).
                3. **Instrução de saída**: Logo antes da pergunta, reforce “Responda apenas em texto, sem JSON”.
                4. **Separação de contexto**: Passe o JSON como parte de um parâmetro de contexto oculto (não incluído no prompt principal) ou utilize a API com parâmetros distintos (`context` vs. `prompt`).
                
                Com esse ajuste, o modelo entenderá que **todo JSON é apenas contexto interno** e não deve aparecer na resposta final.
             
                """;
    }

    @Override
    public IntentType getIntentType(String intentType) {
        return IntentType.PLAYERS;
    }
}
