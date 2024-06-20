package com.task.orders.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.orders.constants.Messages;
import com.task.orders.exception.CommonException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.task.orders.constants.InfoId.INVALID_INPUT_ID;

@Component
@AllArgsConstructor
public class ResponseHandler {
    private final ObjectMapper objectMapper;

    public <T> T mapResponse(ResponseEntity<String> responseEntity, Class<T> reponseClass) throws IOException {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return objectMapper.readValue(responseEntity.getBody(), reponseClass);
        }
        throw new CommonException(INVALID_INPUT_ID,
                Messages.UNABLE_TO_PROCESS_RESPONSE_FROM_THIRD_PARTY +responseEntity.getBody());
    }
}

