package com.task.orders.service.impl;

import com.task.orders.dto.UserData;
import com.task.orders.entity.UserEntity;
import com.task.orders.repository.UserRepo;
import com.task.orders.service.dao.UserServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class UserServiceImpl implements UserServiceDao {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserEntity userSignUp(UserData userData) {
        UserEntity userEntity = UserEntity.build(
                UUID.randomUUID(),
                userData.getName(),
                userData.getPassword(),
                userData.getAge(),
                userData.getEmail(),
                userData.getMobileNumber(),
                userData.getGender()
        );
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity userLogin(String email, String password) {
        return userRepo.findByEmail(email);
    }
}
