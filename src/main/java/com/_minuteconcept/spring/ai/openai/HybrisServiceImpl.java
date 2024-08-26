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
public class HybrisServiceImpl implements Function<HybrisServiceImpl.Request, HybrisServiceImpl.Order> {
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
        urlMap.put("home", homecentreUrl);
    }

    @JsonClassDescription("This  contains userId and website")
    public record Request(String userId, String website) {
    }

    @JsonClassDescription("This  contains orderId and Status")
    public record Response(String orderId, String Status) {
    }

    public Order apply(Request request) {
        try {
            return hybris(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    RestTemplate restTemplate;

    public Order hybris(Request request) throws JsonProcessingException {
        String url = urlMap.get(request.website);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse the JSON
            JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
            JsonNode orderDataList = rootNode.path("orderDataList");


            if (orderDataList.isArray() && orderDataList.size() > 0) {

                JsonNode order = orderDataList.get(0);

                // Extract order code
                String orderCode = order.path("code").asText();

                // Extract delivery address
                JsonNode deliveryAddress = order.path("deliveryAddress");
                String formattedAddress = deliveryAddress.path("formattedAddress").asText();
                String cellphone = deliveryAddress.path("cellphone").asText();
                String email = deliveryAddress.path("email").asText();
                DeliveryAddress d=new DeliveryAddress(formattedAddress,cellphone,email);
                // Extract entries
                JsonNode entries = order.path("entries");
                List<Entry> entryList=new ArrayList<>();
                if (entries.isArray() && entries.size() > 0) {
                    JsonNode entry = entries.get(0);
                    JsonNode product = entry.path("product");
                    String productCode = product.path("code").asText();
                    String orderEntryTatMessage = entry.path("orderEntryTatMessage").asText();
                    JsonNode consignmentStatusNode = entry.path("consignmentStatus");
                    JsonNode historyDataArray = consignmentStatusNode.path("consignmentHistoryData");

                    // Get the last element in the array
                    JsonNode lastElement = historyDataArray.get(historyDataArray.size() - 1);

                    // Extract the status from the last element
                    String lastStatus = lastElement.path("status").asText();

                    Entry entry1=new Entry(new Product(productCode),orderEntryTatMessage,new ConsignmentHistoryData(lastStatus));
                    entryList.add(entry1);
                }
                    // Extract total price
                    JsonNode totalPrice = order.path("totalPrice");
                    double totalPriceValue = totalPrice.path("value").asDouble();

                    // Extract payment mode
                    JsonNode paymentMode = order.path("paymentMode");
                    String paymentModeCode = paymentMode.path("code").asText();


                    return new Order(orderCode, d,entryList,new TotalPrice(totalPriceValue),new PaymentMode(paymentModeCode));



            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;
    }


    @JsonClassDescription("This  contains code deliveryAddress entries totalPrice and paymentMode")
    public record Order(
            String code,
            DeliveryAddress deliveryAddress,
            List<Entry> entries,
            TotalPrice totalPrice,
            PaymentMode paymentMode
    ) {}

    // Represents the delivery address
    public record DeliveryAddress(
            String formattedAddress,
            String cellphone,
            String email
    ) {}

    // Represents an entry in the order
    public record Entry(
            Product product,
            String orderEntryTatMessage,
            ConsignmentHistoryData consignmentStatus
    ) {}

    // Represents a product in an entry
    public record Product(
            String code
    ) {}

    // Represents consignment history data
    public record ConsignmentHistoryData(
            String status
    ) {}

    // Represents the total price
    public record TotalPrice(
            double value
    ) {}

    // Represents the payment mode
    public record PaymentMode(
            String code
    ) {}
}
