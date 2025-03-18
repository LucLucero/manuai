package com.sylvamo.ai.manuai.modules.document.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    //Injetar o useCases
    //inicializar o chatClient

    private final ChatClient chatClient;

    public DocumentController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String initializeChat(@RequestParam(value = "message") String message) {

        String userMessage= message;
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
