package com.task.orders.service.thirdparty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.orders.client.RestTemplateClient;
import com.task.orders.constants.Helpers;
import com.task.orders.constants.TestConstants;
import com.task.orders.thirdparty.response.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import static com.task.orders.constants.TestConstants.TEST_URL;

public class ThirdPartyServiceTest {

    @MockBean
    private RestTemplate restTemplate=new RestTemplate();

    @InjectMocks
    private RestTemplateClient restTemplateClient;

    private MockRestServiceServer mockServer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void clientSetup(){
        MockitoAnnotations.initMocks(this);
        mockServer = MockRestServiceServer.createServer(restTemplate);
        restTemplateClient=new RestTemplateClient(restTemplate);
    }

    @Test
    void apiTest() throws JsonProcessingException {
        var apiRequest=Helpers.apiRequest(TEST_URL, HttpMethod.GET,new HashMap<>());
        Helpers.mockBuildUp(TEST_URL, TestConstants.mockResponse,mockServer);
        var response=restTemplateClient.sendRequest(apiRequest);
        var res=objectMapper.readValue(response.getBody(), ApiResponse.class);
        Assertions.assertEquals(res.getHead().getStatus(), "0");
    }
}
