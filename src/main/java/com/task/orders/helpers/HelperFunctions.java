package com.task.orders.helpers;

import com.task.orders.constants.Constants;
import com.task.orders.redis.RedisHelper;

import java.security.SecureRandom;

import static com.task.orders.constants.Constants.*;
import static com.task.orders.helpers.Crypto.encrypt;

public class HelperFunctions {

    public static String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }

    public static String generateRedisToken(String id, String email, String name, RedisHelper redisHelper) {
        var key= encrypt(id+"//"+email+"//"+name);
        redisHelper.set(Constants.REDIS_KEY+id,key);
        return key;
    }

    public static String maskMobile(String mobileNumber){
        String mid=mobileNumber.substring(Constants.FIVE,Constants.TEN);
        return mobileNumber.replace(mid, MASK);
    }


}
