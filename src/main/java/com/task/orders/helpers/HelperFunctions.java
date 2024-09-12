package com.task.orders.helpers;

import com.task.orders.constants.Constants;
import com.task.orders.entity.UserEntity;
import com.task.orders.jwt.JwtHelper;
import com.task.orders.cache_Redis.RedisHelper;
import com.task.orders.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;


import java.security.SecureRandom;
import java.util.UUID;

import static com.task.orders.constants.Constants.*;
import static com.task.orders.helpers.Crypto.encrypt;

@Component
public class HelperFunctions {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomService userService;

    public static String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }

    @CachePut(key = Constants.REDIS_KEY+"#id",value = "#result",cacheManager = "oneDayCacheManager")
    public static String generateRedisToken(String id, String email, String name, RedisHelper redisHelper) {
        //        redisHelper.set(Constants.REDIS_KEY+id,key);
        return encrypt(id+"//"+email+"//"+name);
    }

    public static String maskMobile(String mobileNumber){
        String mid=mobileNumber.substring(Constants.FIVE,Constants.TEN);
        return mobileNumber.replace(mid, MASK);
    }

    @CachePut(key = "#id",value = "#result",cacheManager = "oneDayCacheManager")
    public String generateJWTToken(UUID id, UserEntity data){
        try {
//            //validating email and password
//            UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(email,password);
//            authenticationManager.authenticate(auth);

            return JwtHelper.generateToken(data.getId().toString(),data.getEmail(),data.getName());
        }catch (BadCredentialsException e){
            throw new BadCredentialsException(" Invalid name or email !!");
        }
    }
}
