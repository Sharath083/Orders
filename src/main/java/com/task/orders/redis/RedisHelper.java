package com.task.orders.redis;


import com.google.gson.Gson;
import com.task.orders.helpers.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisHelper {
    Gson gson =new Gson();
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    public Crypto crypto;

    public <T> T save(String key, int id, T value) {
        redisTemplate.<String, String>opsForHash()
                .put(key, String.valueOf(id).trim(), gson.toJson(value).trim());
        redisTemplate.expire(key,60, TimeUnit.MINUTES);
        return value;
    }


    public <T> T get(String key, String id, Class<T> clazz) {
        String json = (String) redisTemplate.opsForHash().get(key, id);
        return json != null ? gson.fromJson(json, clazz) : null;
    }


    public String delete(String key,String id){
        redisTemplate.opsForHash().delete(key,id);
        return "Product Deleted Successfully";
    }


    public void set(String key,String value){
        redisTemplate.opsForValue().set(key,value,60,TimeUnit.MINUTES);
    }
    public void set(String key,String value,int timeout){
        redisTemplate.opsForValue().set(key,value,timeout,TimeUnit.MINUTES);
    }

    public boolean delete(String key){
        return redisTemplate.delete(key);
    }


    public String get(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

}
