package com._minuteconcept.spring.ai.openai;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class HybrisServiceTATImpl implements Function<HybrisServiceTATImpl.Request, HybrisServiceTATImpl.Response> {
    @Value("${url.lifestyle}")
    private String lifestyleUrl;

    @Value("${url.max}")
    private String maxUrl;

    @Value("${url.home}")
    private String homecentreUrl;
    private final Map<String, String> urlMap = new HashMap<>();

    @PostConstruct
    public void init() {
        urlMap.put("lifestylein", lifestyleUrl);
        urlMap.put("maxin", maxUrl);
        urlMap.put("homecentrein", homecentreUrl);
    }

    @JsonClassDescription("This  contains pincode and productCode for get TAT")
    public record Request(String pincode,String productCode) {
    }

    @JsonClassDescription("This  contains deliveryEstimateMsg and city")
    public record Response(String deliveryEstimateMsg, String city) {
    }

    public Response apply(Request request) {
        try {
            return hybris(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    RestTemplate restTemplate;

    public Response hybris(Request request) throws JsonProcessingException {
        String url = "https://www.lifestylestores.com/landmarkshopscommercews/v2/lifestylein/en/cp/pincode/" +request.pincode+
                "?appId=Desktop&access_token=6a1b216c-ac1c-4799-8df7-55b462e19eec&product="+request.productCode;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.set("Cookie", "lmg_id=798707dfa89121b0e664e74cb5a6772b");

        // Create HttpEntity with headers (no body needed for this request)
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Execute the request
        ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Response.class);

        // Check the response status and body
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Response: " + responseEntity.getBody());
        } else {
            System.out.println("Error: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }



}
