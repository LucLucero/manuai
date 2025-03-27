package com.sylvamo.ai.manuai.modules.document.controller;


import com.sylvamo.ai.manuai.modules.document.useCases.DocsReader;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/document")
public class DocumentController {

    //Injetar o useCases
    //inicializar o chatClient

    private final DocsReader docsReader;

    private final ChatClient chatClient;

    @Autowired
    public DocumentController(ChatClient.Builder chatClientBuilder, View error) {
        this.chatClient = chatClientBuilder.build();
        this.docsReader = new DocsReader();;

    }

    @Async
    @GetMapping("/chat")
    public CompletableFuture<ResponseEntity<Object>> initializeChat(@RequestParam(value = "message") String message) {

        return CompletableFuture.supplyAsync(() -> {
            String userMessage = message;
            System.out.println(userMessage);
            if (Objects.equals(message, "Importar")) {

                try {
                    String content = docsReader.getDocsFromPdf().toString();
                    return ResponseEntity.status(HttpStatus.OK).body(content);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
                }

            } else {
                List<String> setupUserContext = new ArrayList<>();
                setupUserContext.add("Sempre forneça dados bem incisivos e apenas quando tiver certeza do que estiver falando" +
                        "Foque em esclarecer o assunto sempre muito bem detalhado" +
                        "Considere sempre o usuário com nível básico no assunto e que queira chegar no nível expert" +
                        "Inicialize as conversas Com a messagem: 'Olá, tudo bom?  Sempre verifique as informações que são criadas por inteligência artificial'"
                );
                setupUserContext.add(userMessage);
                String content = chatClient.prompt().user(setupUserContext.toString()).call().chatResponse().toString();
                return ResponseEntity.status(HttpStatus.OK).body(content);
            }
        });
    }
}
