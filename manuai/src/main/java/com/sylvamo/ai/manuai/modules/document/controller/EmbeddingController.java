package com.sylvamo.ai.manuai.modules.document.controller;

import jakarta.websocket.server.ServerEndpoint;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/emb")
public class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    @Autowired
    public EmbeddingController(@Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }


    @GetMapping("/ai/emb")
    public Map embed(@RequestParam(value = "message") String message) {

        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(message));

        return Map.of("embedding", embeddingResponse);

        
    }



}
