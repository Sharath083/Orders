package com.task.orders.controller;

import com.task.orders.dto.BaseResponse;
import com.task.orders.service.impl.otp.OtpService;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/otp")
public class OtpController {
    @Autowired
    private OtpService otpService;
    @Autowired
    private RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;
    @GetMapping("/send")
    public ResponseEntity<BaseResponse> sendOtp(){
        String d=redisSessionAuthenticationFilter.getUserData().getUserId();
        UUID userId= UUID.fromString(d);
        return ResponseEntity.ok(new BaseResponse("1",otpService.sendOtp(userId)));
    }
    @GetMapping("/verify/{otp}")
    public ResponseEntity<BaseResponse> verifyOtp(@PathVariable String otp){
        String d=redisSessionAuthenticationFilter.getUserData().getUserId();
        UUID userId= UUID.fromString(d);
        return ResponseEntity.ok(new BaseResponse("1",otpService.validate(otp,userId)));
    }
}
