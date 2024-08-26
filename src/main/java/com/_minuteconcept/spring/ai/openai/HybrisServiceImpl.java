package com._minuteconcept.spring.ai.openai;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class HybrisServiceImpl implements Function<HybrisServiceImpl.Request, HybrisServiceImpl.OrderListWsDTO> {
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

    @JsonClassDescription("This  contains userId and website")
    public record Request(String userId, String website) {
    }

    @JsonClassDescription("This  contains orderId and Status")
    public record Response(String orderId, String Status) {
    }

    public OrderListWsDTO apply(Request request) {
        try {
            return hybris(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    RestTemplate restTemplate;

    public OrderListWsDTO hybris(Request request) throws JsonProcessingException {
        String url = urlMap.get( urlMap.keySet().stream().filter(k->k.contains(request.website)).findFirst().get());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<OrderListWsDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, OrderListWsDTO.class);
        return responseEntity.getBody();
    }


    @JsonClassDescription("This  contains code deliveryAddress entries totalPrice and paymentMode")
    public record OrderListWsDTO(
            List<OrderData> orderDataList
    ) {
        public record OrderData(
                String code,
                DeliveryAddress deliveryAddress,
                List<Entry> entries,
                PaymentMode paymentMode,
                TotalPrice totalPrice
        ) {
            public record DeliveryAddress(
                    String cellphone,
                    String formattedAddress
            ) {}

            public record Entry(
                    ConsignmentStatus consignmentStatus,
                    String orderEntryTatMessage,
                    Product product
            ) {
                public record ConsignmentStatus(
                        String consignmentCode,
                        List<ConsignmentHistoryData> consignmentHistoryData
                ) {
                    public record ConsignmentHistoryData(
                            String formattedDate,
                            boolean isReturnStatus,
                            String status
                    ) {}
                }

                public record Product(
                        String code,
                        String name
                ) {}
            }

            public record PaymentMode(
                    String code
            ) {}

            public record TotalPrice(
                    String formattedValue
            ) {}
        }
    }
}
