package com._minuteconcept.spring.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class MyController {

    record Subsidiary( List<String> subsidiaries) {
    }

    private final ChatClient chatClient;

    public MyController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ai")
    String generation(@RequestParam String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    @GetMapping("/aiChatResponse")
    ChatResponse generationChatResponse(@RequestParam String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .chatResponse();
    }


    @GetMapping("/aiEntityResponse")
    Subsidiary getFilms() {
        Subsidiary actorFilms = chatClient.prompt()
                .user("list of subsidiary for landmarkgroup")
                .call()
                .entity(Subsidiary.class);
        return actorFilms;
    }
}
