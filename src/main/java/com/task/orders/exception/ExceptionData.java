package com.task.orders.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionData {
    private String messageId;
    private String message;
    private Map<String,String> error;
    public ExceptionData(String messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }
}