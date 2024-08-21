package com._minuteconcept.spring.ai.openai.controller;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.*;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class OpenAiImageController {

   private static final ImageOptions options = ImageOptionsBuilder.builder()
            .withModel("dall-e-3")
            .withHeight(1024)
            .withWidth(1024)
            .build();

    private final ImageModel imageModel;
    private final ChatModel chatModel;
    @Autowired
    public OpenAiImageController(ImageModel imageModel,ChatModel chatModel) {
        this.chatModel = chatModel;
        this.imageModel = imageModel;
    }

    /**
     *
     * Use this Scripts to see the image in POSTMAN
     *
     * // Parse the response body
     * var responseJson = responseBody;
     *
     * // Extract the image URL from the response
     * var imageUrl = responseJson.imageUrl; // Replace 'imageUrl' with the actual key in your response
     *
     * // Define the template with the image path
     * var template = `
     * <img src="${responseJson}" alt="Image" style="max-width: 100%; height: auto;">
     * `;
     *
     * // Set the visualizer with the template and the response data
     * pm.visualizer.set(template, {
     *     response: responseJson
     * });
     *
     *
     *
     * @param message
     * @return
     */

    @GetMapping("/image-gen")
    public String imageGen(@RequestParam String message) {
        ImagePrompt imagePrompt = new ImagePrompt(message, options);
        ImageResponse response = imageModel.call(imagePrompt);
        String imageUrl = response.getResult().getOutput().getUrl();

    return   imageUrl  ;
    }




    /**
     * Get Image Description based on pic
     * @param file
     * @return
     * @throws IOException
     */
    @GetMapping("/getDescription")
    public String getDescription(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] imageData = file.getBytes();
        var userMessage = new UserMessage(
                "Based on the image  generate a detailed product description. The description should be casual and trendy, incorporating keywords related to the product.", // content
                List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageData))); // media
        ChatResponse response = chatModel.call(new Prompt(List.of(userMessage)));
        return response.getResult().getOutput().getContent();

    }



}
