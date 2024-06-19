package com.task.orders.client;

import com.task.orders.config.ConfigParam;
import com.task.orders.exception.CommonException;
import com.task.orders.thirdparty.request.ApiRequest;
import com.task.orders.thirdparty.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Service
public class ThirdPartyClient {
    @Autowired
    private ConfigParam configParam;

    private final WebClient webClient;

    private RestTemplate restTemplate;

    public ThirdPartyClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://turing.trendlyne.com")
                .build();
    }

    public Mono<ApiResponse> sendData(ApiRequest request) {
        try {
            return this.webClient
                    .method(request.getHttpMethod())
                    .uri(request.getUrl())
                    .headers(httpHeaders -> request.getHeaders().forEach(httpHeaders::set))

//                .header("Authorization", "Bearer " + apiKey)
//                .body(Mono.just(request), ApiRequest.class)
//                .bodyValue()
                    .retrieve()
                    .bodyToMono(ApiResponse.class);
        }catch (Exception e){
            throw new CommonException("1",e.getMessage());
        }
    }
}
