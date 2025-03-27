package com.sylvamo.ai.manuai.modules.document.controller;


import com.sylvamo.ai.manuai.modules.document.useCases.DocsReader;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    //Injetar o useCases
    //inicializar o chatClient

    private final DocsReader docsReader;

    private final ChatClient chatClient;

    @Autowired
    public DocumentController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.docsReader = new DocsReader();;
    }

    @GetMapping("/chat")
    public String initializeChat(@RequestParam(value = "message") String message) {

        String userMessage= message;
        System.out.println(userMessage);
        if (Objects.equals(message, "Importar")) {

            String content =  docsReader.getDocsFromPdf().toString();
            System.out.println(content);
            return "Responda quantos anos voce tem";
        } else {

            List<String> setupUserContext = new ArrayList<>();
            setupUserContext.add("Sempre forneça dados bem incisivos e apenas quando tiver certeza do que estiver falando" +
                    "Foque em esclarecer o assunto sempre muito bem detalhado" +
                    "Considere sempre o usuário com nível básico no assunto e que queira chegar no nível expert" +
                    "Inicialize as conversas Com a messagem: 'Olá, tudo bom?  Sempre verifique as informações que são criadas por inteligência artificial'"
            );
            setupUserContext.add(userMessage);
            return chatClient.prompt().user(setupUserContext.toString()).call().chatResponse().toString();
        }
    }
}
