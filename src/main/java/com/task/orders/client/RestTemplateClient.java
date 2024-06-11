package com.task.orders.client;

import com.task.orders.thirdparty.request.ApiRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Component
public class RestTemplateClient {
    private final RestTemplate restTemplate;

    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public ResponseEntity<String> sendRequest(ApiRequest request) {
        HttpHeaders headers = new HttpHeaders();
        request.getHeaders().forEach(headers::set);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer your_access_token");
//        // Add more headers if needed
//        var ss=HttpMethod.GET;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                request.getUrl(), request.getHttpMethod(), requestEntity, String.class);
    }
}
