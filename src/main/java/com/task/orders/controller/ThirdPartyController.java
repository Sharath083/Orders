package com.task.orders.controller;


import com.task.orders.constants.ApiEndPoints;
import com.task.orders.constants.Messages;
import com.task.orders.constants.StatusCodes;
import com.task.orders.exception.CommonException;
import com.task.orders.service.impl.thirdparty.ThirdPartyService;
import com.task.orders.service.impl.thirdparty.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.task.orders.constants.InfoId.INVALID_INPUT_ID;

@RestController
@RequestMapping(ApiEndPoints.THIRDPARTY)
@CrossOrigin(origins = "https://calm-kringle-dbfd63.netlify.app")
public class ThirdPartyController {
    @Autowired
    ThirdPartyService service;
    @GetMapping()
    public ApiResponse getResponse() {
        try {
            return service.thirdPartyMethod();
        }catch(Exception e){
            throw new CommonException(INVALID_INPUT_ID, Messages.UNABLE_TO_GET_DATA, StatusCodes.EMPTY);
        }
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
