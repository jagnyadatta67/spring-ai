package com._minuteconcept.spring.ai.openai.controller;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("openai")
public class OpenAIChatModelController {

    @Autowired
    private ChatModel chatModel;

    /**
     *  In this simple example, the user input sets the contents of the user message.
     * The call method sends a request to the AI model, and the content method returns the AI model’s response as a String.
     *
     *  @param userInput
     * @return
     */
    @GetMapping("/ai")
    public String generation(@RequestParam String userInput) {
        return this.chatModel.call(userInput);
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
        var prompt=new Prompt(userInput);
      return this.chatModel.call(prompt);
    }

    /**
     * You often want to return an entity class that is mapped from the returned String.
     * The entity method provides this functionality.
     *
     * For example, given the Java record:
     * @return
     */

    @GetMapping("/aiEntityResponse")
    public Subsidiary getFilms() throws IOException {
        var prompt=new Prompt("list of subsidiary for landmarkgroup");
        BeanOutputConverter<Subsidiary> beanOutputConverter =
                new BeanOutputConverter<>(Subsidiary.class);
        var output=  chatModel.call(prompt).getResult();
        return beanOutputConverter.convert(output.getOutput().getContent());
    }


    @GetMapping("/getDescription")
    public String getDescription(@RequestParam("file")MultipartFile file) throws IOException {
        byte[] imageData = file.getBytes();
        var userMessage = new UserMessage(
                "Explain what do you see in this picture?", // content
                List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageData))); // media
        ChatResponse response = chatModel.call(new Prompt(List.of(userMessage)));
        return response.getResult().getOutput().getContent();

    }





















    /**
     * use of record class java 17 feature for pojo
     * @param subsidiaries
     */
    record Subsidiary( List<String> subsidiaries) {
    }
}
