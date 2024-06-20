package com.task.orders.controller;

import com.task.orders.constants.ApiEndPoints;
import com.task.orders.dto.BaseResponse;
import com.task.orders.service.impl.mail.EmailGenerator;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiEndPoints.MAIL)
public class MailController {
    @Autowired
    EmailGenerator emailGenerator;
    @Autowired
    RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;

    @GetMapping(ApiEndPoints.SEND)
    public ResponseEntity<BaseResponse> sampleMail() {
        var data = redisSessionAuthenticationFilter.getUserData();
        return ResponseEntity.ok(emailGenerator.generateEmail(
                data.getEmail(), UUID.fromString(data.getUserId())));
    }

    @GetMapping(ApiEndPoints.OTP)
    public ResponseEntity<BaseResponse> sendOtp() {
        var d = redisSessionAuthenticationFilter.getUserData();
        return ResponseEntity.ok(
                emailGenerator.sendOtp(d.getEmail(),
                        d.getUserId()));
    }

}
