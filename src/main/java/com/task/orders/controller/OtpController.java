package com.task.orders.controller;

import com.task.orders.config.JwtAuthenticationFilter;
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

public class OtpController {
    @Autowired
    OtpService otpService;
    @Autowired
    RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;

    @GetMapping(ApiEndPoints.SEND)
    public ResponseEntity<BaseResponse> sendOtp() {
//        String d = redisSessionAuthenticationFilter.getUserData().getUserId();
        var data= JwtAuthenticationFilter.getSessionData();
        UUID userId = UUID.fromString(data.getUserId());
        return ResponseEntity.ok(otpService.sendOtp(userId));
    }

    @GetMapping(ApiEndPoints.VERIFY_OTP)
    public ResponseEntity<BaseResponse> verifyOtp(@RequestParam String mail , @RequestParam String otp) {
//        String d = redisSessionAuthenticationFilter.getUserData().getUserId();
//        UUID userId = UUID.fromString(d);
        var data= JwtAuthenticationFilter.getSessionData();
        String gmail=mail==null?data.getEmail():mail;

        UUID userId = UUID.fromString(data.getUserId());
        return ResponseEntity.ok(otpService.validate(otp, gmail));
    }
}
