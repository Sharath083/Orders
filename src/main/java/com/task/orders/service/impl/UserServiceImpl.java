package com.task.orders.service.impl;

import com.task.orders.dto.UserData;
import com.task.orders.entity.UserEntity;
import com.task.orders.repository.UserRepo;
import com.task.orders.service.dao.UserServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserServiceDao {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserEntity userSignUp(UserData userData) {
//        List<UserEntity.UserD> dd=List.of(new UserEntity.UserD(userData.getName(),userData.getPassword()));
        UserEntity userEntity = UserEntity.build(
                UUID.randomUUID(),
                userData.getName(),
                userData.getPassword(),
                userData.getAge(),
                userData.getEmail(),
                userData.getMobileNumber(),
                userData.getGender()
//                ,dd

        );
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity userLogin(String email, String password) {
        return userRepo.findByEmailAndPassword(email,password);
    }
}
