package com._minuteconcept.spring.ai.openai.config;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import com._minuteconcept.spring.ai.openai.HybrisServiceImpl;
import com._minuteconcept.spring.ai.openai.HybrisServiceTATImpl;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;


import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.context.annotation.Description;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import java.util.function.Function;


@Configuration
public class OpenAIChatModelConfig {

    @Bean
    public RestTemplate restTemplate() {

        var restTemplate = new RestTemplate();
        try {
            final TrustStrategy acceptingTrustStrategy = (final X509Certificate[] chain, final String authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

            HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                    .setSSLSocketFactory(socketFactory)
                    .build();

            org.apache.hc.client5.http.impl.classic.CloseableHttpClient c5 = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .evictExpiredConnections()
                    .build();

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(c5);
            restTemplate.setRequestFactory(factory);

        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.getStackTrace();
            throw new RuntimeException("Error in initializing the NonSecureRestTemplate");
        }

        restTemplate.getMessageConverters().add(getMappingJackson2HttpMessageConverter());

        return restTemplate;

    }
@Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        return mappingJackson2HttpMessageConverter;
    }


    @Bean
    @Description("Get the Order code and Status using and userId and website") // function description
    public Function<HybrisServiceImpl.Request, HybrisServiceImpl.OrderListWsDTO> orderStatusService() {
        return new HybrisServiceImpl();
    }

    @Bean
    @Description("Get the Order code and Status using and userId and website") // function description
    public Function<HybrisServiceTATImpl.Request, HybrisServiceTATImpl.Response> getTatService() {
        return new HybrisServiceTATImpl();
    }



}
