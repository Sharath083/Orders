package com.task.orders.service.dao;

import com.task.orders.dto.UserData;
import com.task.orders.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public interface UserServiceDao {
    public UserEntity userSignUp(UserData userData);
    public UserEntity userLogin(String email, String password);
}
