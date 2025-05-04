// filepath: c:\Users\danil\OneDrive\Documentos\FURIA\furia-chat-bot-backend\src\main\java\br\com\danilo\furiachatbot\controller\ChatController.java
package br.com.danilo.furiachatbot.controller;

import br.com.danilo.furiachatbot.model.MessageRequest;
import br.com.danilo.furiachatbot.model.MessageResponse;
import br.com.danilo.furiachatbot.services.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat Bot", description = "API para interação com o Chat Bot da FURIA") 
public class ChatController {


    private final ChatService chatService;

    @PostMapping
    @Operation( // Descrição da operação
            summary = "Envia uma mensagem do usuário para o Chat Bot",
            description = "Recebe a mensagem do usuário, processa para identificar a intenção, busca informações relevantes e retorna a resposta gerada pela IA."
    )
    @ApiResponses(value = { // Descrição das respostas possíveis
            @ApiResponse(responseCode = "200", description = "Resposta do bot processada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar a mensagem",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class), 
                            examples = @ExampleObject(value = "{\"intent\":\"GENERAL\",\"botResponse\":\"Desculpe, tive um problema ao buscar as informações.\",\"context\":null}")))
    })
    public Mono<MessageResponse> chat(
            @io.swagger.v3.oas.annotations.parameters.RequestBody( 
                    description = "Objeto contendo a mensagem enviada pelo usuário.",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageRequest.class),
                            examples = @ExampleObject(value = "{\"userMessage\": \"Quem são os jogadores da Furia?\"}"))
            )
            @RequestBody MessageRequest request) {
        return chatService.getBotResponse(request.userMessage());
    }
}