package com.task.orders.service.impl.otp;

import com.task.orders.config.ConfigParam;
import com.task.orders.config.MyConfig;
import com.task.orders.constants.Messages;
import com.task.orders.constants.StatusCodes;
import com.task.orders.dto.BaseResponse;
import com.task.orders.entity.UserEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.helpers.Crypto;
import com.task.orders.helpers.HelperFunctions;
import com.task.orders.redis.RedisHelper;
import com.task.orders.repository.UserRepo;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.task.orders.constants.Constants.*;
import static com.task.orders.constants.InfoId.INVALID_INPUT_ID;
import static com.task.orders.constants.InfoId.VALID;

@Service
public class OtpService {
    @Autowired
    ConfigParam configParam;
    @Autowired
    RedisHelper redisHelper;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MyConfig myConfig;



    public BaseResponse sendOtp(UUID userId) {
        try {
            String otp = HelperFunctions.generateOtp();
            myConfig.twilioConnect(configParam.getTwilioId(), configParam.getTwilioToken());
            String msg = configParam.getMessage().replace("?", otp);
            UserEntity userEntity = userRepo.findById(userId).orElse(null);
            String phoneNumber = userEntity.getPhone();
            MessageCreator creator = Message.creator(
                    new PhoneNumber(CODE + Crypto.decrypt(phoneNumber)),
                    new PhoneNumber(configParam.getTwilioPhone()),
                    msg
            );
            creator.create();
            storeOtp(userId,otp);
            return new BaseResponse(VALID,
                    Messages.OTP_SENT_SUCCESSFULLY +
                            HelperFunctions.maskMobile(creator.create().getTo()));
        } catch (ApiException e) {
            throw new CommonException(INVALID_INPUT_ID, Messages.UNABLE_TO_SEND_OTP, StatusCodes.SUCCESS);
        }
    }

    private void storeOtp(UUID userId,String otp) {
        redisHelper.set(OTP_REDIS_KEY + userId, otp, 5);
    }

    public BaseResponse validate(String otp, UUID userId) throws CommonException {
        var s = redisHelper.get(OTP_REDIS_KEY + userId);
        if (s != null && s.equals(otp)) {
            return new BaseResponse(VALID, Messages.YOUR_OTP_IS_VERIFIED);
        } else if (s == null) {
            throw new CommonException(INVALID_INPUT_ID,
                    Messages.YOUR_OTP_HAS_EXPIRED, StatusCodes.BAD_REQUEST);
        } else {
            throw new CommonException(INVALID_INPUT_ID,
                    Messages.INVALID_OTP, StatusCodes.BAD_REQUEST);
        }
    }
}
