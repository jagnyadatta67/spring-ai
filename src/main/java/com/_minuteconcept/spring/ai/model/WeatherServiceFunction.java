package com._minuteconcept.spring.ai.model;

import org.springframework.http.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    public static final String WEATHER_URL = "https://api.api-ninjas.com/v1/weather";
    private final String apiNinjasKey;

    RestTemplate restTemplate ;

    public WeatherServiceFunction(String apiNinjasKey) {
        this.apiNinjasKey = apiNinjasKey;
    }

    @Override
    public WeatherResponse apply(WeatherRequest weatherRequest) {
        RestClient restClient = RestClient.builder()
                .baseUrl(WEATHER_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", apiNinjasKey);
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                }).build();

        WeatherResponse r=   restClient.get().uri(uriBuilder -> {
            System.out.println("Building URI for weather request: " + weatherRequest);
            uriBuilder.queryParam("lat", weatherRequest.lat());
            uriBuilder.queryParam("lon", weatherRequest.lon());
            return uriBuilder.build();
        }).retrieve().body(WeatherResponse.class);

        return r;
    }

    public void HybrisCall(){


        // Set up the request URL
        String url = "https://maxin.local:9002/landmarkshopscommercews/in/oauth/token";

        // Create headers and set Content-Type and Cookie
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Cookie", "JSESSIONID=6D84569C7633695DC572A191B414D1BB");

        // Create request body
        Map<String, String> body = new HashMap<>();
        body.put("appId", "ANDROID");
        body.put("client_id", "mobile_android");
        body.put("client_secret", "F7LBBekbehGRQWpROIKJq");
        body.put("grant_type", "password");
        body.put("username", "saroj.pradhan@landmarkgroup.in");
        body.put("password", "585858pradhan");

        // Create HttpEntity with headers and body
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Make the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Check response status and body
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Response: " + responseEntity.getBody());
        } else {
            System.out.println("Failed with status code: " + responseEntity.getStatusCode());
        }
    }
}