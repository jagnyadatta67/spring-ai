package com._minuteconcept.spring.ai.controller.openai.config;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("openai")
public class OpenAIChatModelConfig {
   /*   @Bean
  public ChatModel ollamaChatModel() {
        var ollamaApi = new OpenAiApi();
        var chatModel = new OllamaChatModel(ollamaApi,
                OllamaOptions.create()
                        .withModel(OllamaOptions.DEFAULT_MODEL)
                        .withTemperature(0.9f));
        return chatModel;

    }*/
}
