package com.task.orders.service.impl;

import com.task.orders.constants.Constants;
import com.task.orders.constants.InfoId;
import com.task.orders.constants.Messages;
import com.task.orders.constants.StatusCodes;
import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.UserData;
import com.task.orders.entity.UserEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.helpers.Crypto;
import com.task.orders.cache_Redis.RedisHelper;
import com.task.orders.repository.UserRepo;
import com.task.orders.service.dao.UserServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.task.orders.constants.InfoId.INVALID_INPUT_ID;
import static com.task.orders.constants.InfoId.VALID;

@Service
public class UserServiceImpl implements UserServiceDao {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RedisHelper redisHelper;
    @Override
    public UserEntity userSignUp(UserData userData) {
//        List<UserEntity.UserD> dd=List.of(new UserEntity.UserD(userData.getName(),userData.getPassword()));
        UserEntity userEntity = UserEntity.build(
                UUID.randomUUID(),
                userData.getName(),
                Crypto.encrypt(userData.getPassword()),
                userData.getAge(),
                userData.getEmail(),
                Crypto.encrypt(userData.getMobileNumber()),
                userData.getGender()
//                ,dd
        );
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity userLogin(String email, String password) {
        System.out.println(Crypto.decrypt("MTIzNDU2Nzg5MA==")+" ---------------------------");
        String enPassword=Crypto.encrypt(password);
        var data= userRepo.findByEmailAndPassword(email,enPassword);
        if(data!=null){
            return data;
        }else{
            throw new CommonException(InfoId.INVALID_INPUT_ID,
                    Messages.INVALID_EMAIL_OR_PASSWORD,
                    StatusCodes.UNAUTHORIZED);
        }
    }
    @Override
    public BaseResponse userLogout(String userId){
        if(redisHelper.delete(Constants.REDIS_KEY+userId)){
            return new BaseResponse(VALID, Messages.LOGOUT_SUCCESSFULLY);
        }
        return new BaseResponse(INVALID_INPUT_ID, Messages.LOGOUT_FAILED);
    }
}
