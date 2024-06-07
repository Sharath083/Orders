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
public class CommonException extends RuntimeException {
    private String infoId;
    private String message;
    private Map<String,String> error;
    public CommonException(String message, String infoId, Map<String, String> error) {
        super(message);
        this.infoId = infoId;
        this.error = error;
    }

//    public CommonException(String infoId,String message) {
//        super(message);
//        this.infoId = infoId;
//    }
    public CommonException(String infoId,String message) {
        this.infoId = infoId;
        this.message=message;

    }
}
