package com.task.orders.controller;

import com.task.orders.dto.BaseResponse;
import com.task.orders.service.impl.mail.EmailGenerator;
import com.task.orders.service.impl.otp.OtpService;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private EmailGenerator emailGenerator;
    @Autowired
    private RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;
    @GetMapping("/send")
    public ResponseEntity<BaseResponse> sampleMail(){
        String email=redisSessionAuthenticationFilter.getUserData().getEmail();
        return ResponseEntity.ok(new BaseResponse("1",emailGenerator.generateEmail(email)));
    }
    @GetMapping("/otp")
    public ResponseEntity<BaseResponse> sendOtp(){
        var d=redisSessionAuthenticationFilter.getUserData();
        return ResponseEntity.ok(new BaseResponse("1",
                emailGenerator.sendOtp(d.getEmail(),
                        d.getUserId())));
    }

}
