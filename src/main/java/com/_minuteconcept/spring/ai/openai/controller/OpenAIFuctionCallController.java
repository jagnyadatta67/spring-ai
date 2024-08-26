package com._minuteconcept.spring.ai.openai.controller;


import com._minuteconcept.spring.ai.openai.HybrisServiceImpl;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OpenAIFuctionCallController {

    @Autowired
    private ChatModel chatModel;
    @Autowired
    HybrisServiceImpl hybrisService;

     @GetMapping("/getHybrisOrder")
    public String getOrder(Question question){
    UserMessage userMessage = new UserMessage(question.question());
    ChatResponse response = chatModel.call(new Prompt(List.of(userMessage),
                    OpenAiChatOptions.builder().withFunction("orderStatusService").build()));
    return  response.getResult().getOutput().getContent();
    }

    @GetMapping("/getTAT")
    public String getTAT(Question question){
        UserMessage userMessage = new UserMessage(question.question());
        ChatResponse response = chatModel.call(new Prompt(List.of(userMessage),
                OpenAiChatOptions.builder().withFunction("getTatService").build()));
        return  response.getResult().getOutput().getContent();
    }



}
