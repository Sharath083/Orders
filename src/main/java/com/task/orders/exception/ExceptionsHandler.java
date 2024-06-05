package com.task.orders.exception;

import com.task.orders.helpers.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {
//    @ExceptionHandler({RuntimeException.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public CommonException handleException(RuntimeException ex){
//        return new CommonException("111", ex.getCause().toString());
//    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonException handleException(CommonException ex){
        return ex;

    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionData handleException(MethodArgumentNotValidException ex){
        Map<String,String> error=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(er->{
                    error.put(er.getField(),er.getDefaultMessage());
                }
        );
        return new ExceptionData(Constants.INVALID_INPUT_ID,Constants.INVALID_INPUT_MSG,error);
    }

}
