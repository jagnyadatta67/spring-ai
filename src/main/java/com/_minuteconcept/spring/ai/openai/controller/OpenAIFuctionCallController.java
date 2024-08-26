package com._minuteconcept.spring.ai.openai.controller;

import com._minuteconcept.spring.ai.model.WeatherRequest;
import com._minuteconcept.spring.ai.model.WeatherResponse;
import com._minuteconcept.spring.ai.model.WeatherServiceFunction;
import com._minuteconcept.spring.ai.openai.HybrisServiceImpl;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
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
@GetMapping("/getWeather")
    public String getWeather(Question question){
    UserMessage userMessage = new UserMessage(question.question());
    ChatResponse response = chatModel.call(new Prompt(List.of(userMessage),
                    OpenAiChatOptions.builder().withFunction("orderStatusService").build()));

      return  response.getResult().getOutput().getContent();


    }
}
