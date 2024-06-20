package com.task.orders.exception;

import com.task.orders.constants.InfoId;
import com.task.orders.constants.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Object> handleException(CommonException ex){
        return error(ex.getInfoId(),ex.getMessage(),ex.getStatusCode());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionData handleException(HttpRequestMethodNotSupportedException ex){
        System.out.println(ex.getMessage());
        return new ExceptionData(InfoId.INVALID_INPUT_ID,ex.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionData handleException(MethodArgumentNotValidException ex){
        Map<String,String> error=new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(er-> error.put(er.getField(),er.getDefaultMessage())
        );
        return new ExceptionData(InfoId.INVALID_INPUT_ID, Messages.INVALID_INPUT_MSG,error);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionData handleException(IllegalArgumentException ex){
        return new ExceptionData(HttpStatus.BAD_REQUEST.toString(),ex.getMessage());
    }
    private ResponseEntity<Object> error(String infoID, String msg,int status) {
        ExceptionData body = new ExceptionData(infoID,msg);
        return ResponseEntity.status(status).body(body);
    }
}
