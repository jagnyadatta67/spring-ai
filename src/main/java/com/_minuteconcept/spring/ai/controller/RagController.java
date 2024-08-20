package com._minuteconcept.spring.ai.controller;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {

    private final OpenAiChatModel openAiChatModel;
    private final VectorStore vectorStore;

    public RagController( VectorStore vectorStore, OpenAiChatModel openAiChatModel) {
        this.vectorStore = vectorStore;
        this.openAiChatModel=openAiChatModel;
    }

    @GetMapping("/robobrain/rag")
    public ResponseEntity<String> generateAnswer(@RequestParam String query) {
        List<Document> similarDocuments = vectorStore.similaritySearch(query);
        String information = similarDocuments.stream()
                .map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));
        var systemPromptTemplate = new SystemPromptTemplate(
                """
                    Answer the given question only using the information in the provided terms. 
                    
                    If you dont know the answer Please respond "I dont know "
                    
                    {information}
                            
                        """);
        System.out.println(information);
        var systemMessage = systemPromptTemplate.createMessage(Map.of("information", information));
        var userPromptTemplate = new PromptTemplate("{query}");
        var userMessage = userPromptTemplate.createMessage(Map.of("query", query));
        var prompt = new Prompt(List.of(systemMessage, userMessage));

        return ResponseEntity.ok(openAiChatModel.call(
                prompt
        ).getResult().getOutput().getContent());
    }
}
