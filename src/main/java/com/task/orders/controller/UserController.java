package com.task.orders.controller;

import com.task.orders.config.ConfigParam;
import com.task.orders.config.MyConfig;
import com.task.orders.config.SecurityConfig;
import com.task.orders.constants.ApiEndPoints;
import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.LoginReq;
import com.task.orders.dto.UserData;
import com.task.orders.entity.UserEntity;
import com.task.orders.constants.Constants;
import com.task.orders.helpers.HelperFunctions;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import com.task.orders.service.dao.UserServiceDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;




@RestController
@RequestMapping(ApiEndPoints.USER)
@CrossOrigin(origins = "https://calm-kringle-dbfd63.netlify.app")
public class UserController {

    @Autowired
    private ConfigParam configParam;
    @Autowired
    UserServiceDao userServiceDao;
    @Autowired
    MyConfig myConfig;
    @Autowired
    RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;

    @PostMapping(ApiEndPoints.SIGNUP)
    public UserEntity signup(@RequestBody @Valid UserData userData) {
        return userServiceDao.userSignUp(userData);
    }



    @PostMapping(ApiEndPoints.LOGIN)

    public HashMap<String, String> login(@RequestBody LoginReq loginReq) {
        var data = userServiceDao.userLogin(loginReq.getEmail(), loginReq.getPassword());
        var token = HelperFunctions.generateRedisToken(data.getId().toString(), data.getEmail(), data.getName(), myConfig.redisHelper);
        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.TOKEN, token);
        return map;
    }

    @PostMapping(ApiEndPoints.LOGOUT)
    public BaseResponse logout() {
        var userId = redisSessionAuthenticationFilter.getUserData().getUserId();
        return userServiceDao.userLogout(userId);
    }
}

