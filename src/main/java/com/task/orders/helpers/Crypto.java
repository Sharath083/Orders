package com.task.orders.helpers;

import com.task.orders.constants.Constants;
import com.task.orders.redis.RedisHelper;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Crypto {
    public static String encrypt(String data){
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
    public static String decrypt(String data){
        return new String(Base64.getDecoder().decode(data));
    }

}
