package com.task.orders.client;

import com.task.orders.thirdparty.request.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateClient {
    @Autowired
    private final RestTemplate restTemplate;

    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> sendRequest(ApiRequest request) {
        HttpHeaders headers = new HttpHeaders();
        request.getHeaders().forEach(headers::set);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                request.getUrl(),
                request.getHttpMethod(),
                requestEntity,
                String.class
        );
    }
}
