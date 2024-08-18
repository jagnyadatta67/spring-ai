package com._minuteconcept.spring.ai.controller;

import com._minuteconcept.spring.ai.service.ChatServiceLocator;
import com._minuteconcept.spring.ai.helper.ChatServiceContextHolder;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChatServiceController {
    @Resource
    ChatServiceLocator chatServiceLocator;

    /**
     *  In this simple example, the user input sets the contents of the user message.
     * The call method sends a request to the AI model, and the content method returns the AI model’s response as a String.
     *
     */
    @GetMapping("/ai")
    public String generation(@RequestParam String userInput) {

        return chatServiceLocator.getService(ChatServiceContextHolder.getChatService()).call(userInput);
    }

    /**
     * An example to return the ChatResponse object that contains
     * the metadata is shown below by invoking chatResponse() after the call() method.
     *
     */

    @GetMapping("/aiChatResponse")
    public ChatResponse generationChatResponse(@RequestParam String userInput) {
        var prompt=new Prompt(userInput);
        return chatServiceLocator.getService(ChatServiceContextHolder.getChatService()).call(prompt);
    }

    /**
     * You often want to return an entity class that is mapped from the returned String.
     * The entity method provides this functionality.
     * <p>
     * For example, given the Java record:
     */

    @GetMapping("/aiEntityResponse")
    public Subsidiary getFilms() {
        var prompt=new Prompt("list of subsidiary for landmarkgroup");
        BeanOutputConverter<Subsidiary> beanOutputConverter =
                new BeanOutputConverter<>(Subsidiary.class);
        var output= this.chatServiceLocator.getService(ChatServiceContextHolder.getChatService()).call(prompt).getResult();
        return beanOutputConverter.convert(output.getOutput().getContent());
    }



    @GetMapping(value = "/aiStylist", produces ="application/json" )
    public List<Outfit> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        BeanOutputConverter<List<Outfit>> outputConverter = new BeanOutputConverter<>(
                new ParameterizedTypeReference<List<Outfit>>() { });

        Prompt prompt = getPrompt(message, outputConverter);


        Generation generation = this.chatServiceLocator.getService(ChatServiceContextHolder.getChatService()).call(prompt).getResult();

        return outputConverter.convert(generation.getOutput().getContent());

    }



    private static Prompt getPrompt(String message, BeanOutputConverter<List<Outfit>> outputConverter) {
        String format = outputConverter.getFormat();
        String template = """
          top 3 best outfit with outfit name for {message} shirt in {format}
        """;

        return new Prompt(new PromptTemplate(template,
                Map.of("format", format
                        ,               "message", message)
        ).createMessage());
    }





































    /**
     * use of record class java 17 feature for pojo
     * @param subsidiaries
     */
    public record Subsidiary( List<String> subsidiaries) {
    }


    public record Outfit(String outfitName, List<Item> items) {}

    // Record class for each item in the outfit
    public record Item(String itemType, String style, String color) {}
}
