package com.task.orders.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.orders.exception.CommonException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@AllArgsConstructor
public class ResponseHandler {
    private final ObjectMapper objectMapper;

    public <T> T mapResponse(ResponseEntity<String> responseEntity, Class<T> reponseClass) throws IOException {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return objectMapper.readValue(responseEntity.getBody(), reponseClass);
        }
        throw new CommonException("-1","Unable to process response "+responseEntity.getBody());
    }
}

