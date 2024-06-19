package com.task.orders.service.impl.otp;

import com.task.orders.config.ConfigParam;
import com.task.orders.config.MyConfig;
import com.task.orders.exception.CommonException;
import com.task.orders.helpers.HelperFunctions;
import com.task.orders.redis.RedisHelper;
import com.task.orders.repository.UserRepo;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.task.orders.helpers.Constants.*;

@Service
public class OtpService {
    @Autowired
    private ConfigParam configParam;
    @Autowired
    RedisHelper redisHelper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MyConfig myConfig;
    private String otp = HelperFunctions.generateOtp();

    public String sendOtp(UUID userId){
        myConfig.twilioConnect(configParam.getTwilioId(),configParam.getTwilioToken());
        String msg=configParam.getMessage().replace("?",otp);
        String phoneNumber=userRepo.findById(userId).get().getPhone();
        Message message = Message.creator(
                new PhoneNumber("+91"+phoneNumber),
                new PhoneNumber(configParam.getTwilioPhone()),
                msg
        ).create();
        storeOtp(userId);
        return msg;
    }

    private void storeOtp(UUID userId){
        redisHelper.set(OTP_REDIS_KEY+userId,otp,5);
    }

    public String validate(String otp,UUID userId) throws CommonException{
        var s=redisHelper.get(OTP_REDIS_KEY+userId);
        if(s!=null && s.equals(otp)){
            return  "Your OTP is verified";
        }
        else if(s==null){
            throw new CommonException(HttpStatus.BAD_REQUEST.toString(),
                    "Your OTP has expired");
        }
        else{
            throw new CommonException(HttpStatus.BAD_REQUEST.toString(),
                    "Invalid OTP ");
        }
    }


}
