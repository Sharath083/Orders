package com.task.orders.controller;

import com.task.orders.config.MyConfig;
import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.LoginReq;
import com.task.orders.dto.UserData;
import com.task.orders.entity.UserEntity;
import com.task.orders.helpers.Constants;
import com.task.orders.redis.RedisHelper;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import com.task.orders.service.dao.UserServiceDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceDao userServiceDao;
    @Autowired
    private MyConfig myConfig;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;
    @PostMapping("/signup")
    public UserEntity signup(@RequestBody @Valid UserData userData){
        return userServiceDao.userSignUp(userData);
    }
    @PostMapping("/login")
    public HashMap<String,String> login(@RequestBody LoginReq loginReq){
        var data=userServiceDao.userLogin(loginReq.getEmail(),loginReq.getPassword());
        var token=myConfig.generateRedisToken(data.getId().toString(),data.getEmail(), data.getName());
        HashMap<String,String> map=new HashMap<>();
        map.put("token",token);
        return map;
    }
    @PostMapping("/logout")
    public BaseResponse logout(){
        var userId=redisSessionAuthenticationFilter.getUserData().getUserId();
        if(redisHelper.delete(Constants.REDIS_KEY+userId)){
            return new BaseResponse("1","Logout Successfully");
        }
        return new BaseResponse("0","Logout Failed");
    }


}

