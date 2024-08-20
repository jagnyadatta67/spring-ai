package com._minuteconcept.spring.ai.openai.config;


import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("openai")
public class OpenAIChatModelConfig {

  public ChatModel ollamaChatModel() {
      var openAiApi = new OpenAiApi(System.getenv("OPENAI_API_KEY"));
      var openAiChatOptions = OpenAiChatOptions.builder()
              .withModel("gpt-3.5-turbo")
              .withTemperature(0.4f)
              .withMaxTokens(200)
              .build();
      var chatModel = new OpenAiChatModel(openAiApi, openAiChatOptions);
        return chatModel;

    }



    @Bean
    ImageModel imageModel(@Value("${spring.ai.openai.api-key}") String apiKey) {
        return new OpenAiImageModel(new OpenAiImageApi(apiKey));
    }



}
