package com.task.orders.controller;


import com.task.orders.dto.BaseResponse;
import com.task.orders.exception.CommonException;
import com.task.orders.thirdparty.ThirdPartyService;
import com.task.orders.thirdparty.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/thirdparty")
public class ThirdParty {
    @Autowired
    private ThirdPartyService service;
    @GetMapping()
    public ApiResponse getResponse() throws IOException {
        var s= service.thirdPartyMethod();
//        BaseResponse response= new BaseResponse();
        ApiResponse response=new ApiResponse();
        response.setInfoId(HttpStatus.OK.toString());
        response.setMessage("Fetched Data from api ");
        response.setHead(s.getHead());
        response.setBody(s.getBody());
        return response;

//        responseMono.subscribe(apiResponse -> {
//            re.set(apiResponse);
//
//            System.out.println("Response Head: " + apiResponse.getHead());
//            System.out.println("Response Body: " + apiResponse.getBody());
//        }, throwable -> {
//            // Handle error
//            throw new CommonException("Error occurred: " + throwable.getMessage(), "throwable");
//        });
//        return re.get();
    }

}
