package com._minuteconcept.spring.ai.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean<ChatServiceFilter> myCustomFilter() {
        FilterRegistrationBean<ChatServiceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ChatServiceFilter());
        registrationBean.addUrlPatterns("/*"); // Apply filter to all URLs
        return registrationBean;
    }
}
