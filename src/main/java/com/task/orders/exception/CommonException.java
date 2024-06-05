package com.task.orders.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonException extends Exception {
    private String infoId;

    public CommonException(String message, String infoId, Map<String, String> error) {
        super(message);
        this.infoId = infoId;
        this.error = error;
    }

    private String message;
    private Map<String,String> error;

    public CommonException(String infoId,String message) {
        super(message);
        this.infoId = infoId;
    }
}
