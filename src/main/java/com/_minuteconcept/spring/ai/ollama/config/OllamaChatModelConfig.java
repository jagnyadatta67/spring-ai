package com._minuteconcept.spring.ai.ollama.config;


import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("ollama")
public class OllamaChatModelConfig {
    @Bean
    public ChatModel ollamaChatModel() {
        var ollamaApi = new OllamaApi();
        var chatModel = new OllamaChatModel(ollamaApi,
                OllamaOptions.create()
                        .withModel(OllamaOptions.DEFAULT_MODEL)
                        .withTemperature(0.9f));
        return chatModel;

    }
}
