package com._minuteconcept.spring.ai.helper;

import com._minuteconcept.spring.ai.filter.ChatServiceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatServiceContextHolder {
   static Logger logger = LoggerFactory.getLogger(ChatServiceContextHolder.class);
    private static final ThreadLocal<String> chatService = new ThreadLocal<>();

    public static void setChatService(String chatmodel) {
        logger.info("chatmodel : {}", chatmodel);
        chatService.set(chatmodel);
    }

    public static String getChatService() {
        logger.info("chatmodel : {}", chatService.get());
        return chatService.get();
    }

    public static void clear() {
        chatService.remove();
    }
}


