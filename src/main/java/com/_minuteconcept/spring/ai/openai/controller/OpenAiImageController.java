package com._minuteconcept.spring.ai.openai.controller;

import org.springframework.ai.image.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

@RestController
public class OpenAiImageController {

   private static final ImageOptions options = ImageOptionsBuilder.builder()
            .withModel("dall-e-3")
            .withHeight(1024)
            .withWidth(1024)
            .build();
    private final ImageModel imageModel;
    private final RestTemplate restTemplate;
    @Autowired
    public OpenAiImageController(ImageModel imageModel, RestTemplate restTemplate) {
        this.imageModel = imageModel;
        this.restTemplate=restTemplate;
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


}
