package com._minuteconcept.spring.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Using an autoconfigured ChatClient.Builder
 * In the most simple use case, Spring AI provides Spring Boot autoconfiguration,
 * creating a prototype ChatClient.Builder bean for you to inject into your class. Here is a simple example of retrieving a String response to a simple user request.
 */
@RestController
public class DefaultChatClientController {



    private final ChatClient chatClient;

    public DefaultChatClientController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     *  In this simple example, the user input sets the contents of the user message.
     * The call method sends a request to the AI model, and the content method returns the AI model’s response as a String.
     *
     *  @param userInput
     * @return
     */
    @GetMapping("/ai")
   public String generation(@RequestParam String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    /**
     * An example to return the ChatResponse object that contains
     * the metadata is shown below by invoking chatResponse() after the call() method.
     *
     * @param userInput
     * @return
     */

    @GetMapping("/aiChatResponse")
    public ChatResponse generationChatResponse(@RequestParam String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .chatResponse();
    }

    /**
     * You often want to return an entity class that is mapped from the returned String.
     * The entity method provides this functionality.
     *
     * For example, given the Java record:
     * @return
     */

    @GetMapping("/aiEntityResponse")
    public Subsidiary getFilms() {
        Subsidiary actorFilms = chatClient.prompt()
                .user("list of subsidiary for landmarkgroup")
                .call()
                .entity(Subsidiary.class);
        return actorFilms;
    }





















    /**
     * use of record class java 17 feature for pojo
     * @param subsidiaries
     */
    record Subsidiary( List<String> subsidiaries) {
    }
}
