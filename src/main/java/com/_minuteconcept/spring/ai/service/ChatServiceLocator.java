package com._minuteconcept.spring.ai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ChatServiceLocator {

    Logger logger = LoggerFactory.getLogger(ChatServiceLocator.class);

    private final Map<String, ChatModel> serviceMap;

    @Autowired
    public ChatServiceLocator(Map<String, ChatModel> serviceMap) {
        this.serviceMap = serviceMap;
        logger.info(serviceMap.toString());
    }

    public ChatModel getService(String key) {
        logger.info("looking for key "+key);
        return serviceMap.get(key);
    }
}
