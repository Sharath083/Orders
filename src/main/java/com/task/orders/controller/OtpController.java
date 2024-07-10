package com.task.orders.controller;

import com.task.orders.constants.ApiEndPoints;
import com.task.orders.dto.BaseResponse;
import com.task.orders.service.impl.otp.OtpService;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiEndPoints.OTP)
@CrossOrigin(origins = "https://calm-kringle-dbfd63.netlify.app")

public class OtpController {
    @Autowired
    OtpService otpService;
    @Autowired
    RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;

    @GetMapping(ApiEndPoints.SEND)
    public ResponseEntity<BaseResponse> sendOtp() {
        String d = redisSessionAuthenticationFilter.getUserData().getUserId();
        UUID userId = UUID.fromString(d);
        return ResponseEntity.ok(otpService.sendOtp(userId));
    }

    @GetMapping(ApiEndPoints.VERIFY_OTP)
    public ResponseEntity<BaseResponse> verifyOtp(@PathVariable String otp) {
        String d = redisSessionAuthenticationFilter.getUserData().getUserId();
        UUID userId = UUID.fromString(d);
        return ResponseEntity.ok(otpService.validate(otp, userId));
    }
}
