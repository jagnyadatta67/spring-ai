package com._minuteconcept.spring.ai.filter;


import com._minuteconcept.spring.ai.helper.ChatServiceContextHolder;
import com._minuteconcept.spring.ai.service.ChatServiceLocator;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ChatServiceFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(ChatServiceFilter.class);
    private static final String OPENAI="openai";
    private static final String OPENAI_CHAT_MODEL= "openAiChatModel";
    private static final String OLLAMA_CHAT_MODEL= "ollamaChatModel" ;



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // Extract the language from request header or parameter
        String uri = httpRequest.getHeader("chatModel");
        if ( OPENAI.equalsIgnoreCase(uri)) {
            uri = OPENAI_CHAT_MODEL; // default language if none provided
        }else{
            uri = OLLAMA_CHAT_MODEL;
        }
        logger.info("uri is {}", uri);
        // Store the language in the context
        ChatServiceContextHolder.setChatService(uri);

        // Continue with the filter chain
        filterChain.doFilter(servletRequest, servletResponse);
    }
}