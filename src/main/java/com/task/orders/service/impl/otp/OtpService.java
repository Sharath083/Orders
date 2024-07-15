package com.task.orders.service.impl.otp;

import com.task.orders.config.ConfigParam;
import com.task.orders.config.MyConfig;
import com.task.orders.constants.Messages;
import com.task.orders.constants.StatusCodes;
import com.task.orders.dto.BaseResponse;
import com.task.orders.entity.OtpEntity;
import com.task.orders.entity.UserEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.helpers.Crypto;
import com.task.orders.helpers.HelperFunctions;
import com.task.orders.repository.OtpRepo;
import com.task.orders.repository.UserRepo;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.task.orders.constants.Constants.*;
import static com.task.orders.constants.InfoId.INVALID_INPUT_ID;
import static com.task.orders.constants.InfoId.VALID;

@Service
public class OtpService {
    @Autowired
    ConfigParam configParam;
//    @Autowired
//    RedisHelper redisHelper;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MyConfig myConfig;
    @Autowired
    OtpRepo otpRepo;





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
            storeOtp(userEntity.getEmail(),otp);
            return new BaseResponse(VALID,
                    Messages.OTP_SENT_SUCCESSFULLY +
                            HelperFunctions.maskMobile(creator.create().getTo()));
        } catch (ApiException e) {
            throw new CommonException(INVALID_INPUT_ID, Messages.UNABLE_TO_SEND_OTP, StatusCodes.SUCCESS);
        }
    }

    public void storeOtp(String mail, String otp){
        OtpEntity otpData=otpRepo.findByMail(mail).orElse(new OtpEntity());
        if(otpData.getOtp()==null) {
            OtpEntity newOtpData = new OtpEntity();
            newOtpData.setMail(mail);
            newOtpData.setOtp(otp);
            otpRepo.save(newOtpData);
        }else{
            otpData.setOtp(otp);
            otpRepo.save(otpData);
        }
    }

//    private void storeOtp(UUID userId,String otp) {
//        redisHelper.set(OTP_REDIS_KEY + userId, otp, 5);
//    }

//    public BaseResponse validate(String otp, UUID userId) throws CommonException {
//        var s = redisHelper.get(OTP_REDIS_KEY + userId);
//        if (s != null && s.equals(otp)) {
//            return new BaseResponse(VALID, Messages.YOUR_OTP_IS_VERIFIED);
//        } else if (s == null) {
//            throw new CommonException(INVALID_INPUT_ID,
//                    Messages.YOUR_OTP_HAS_EXPIRED, StatusCodes.BAD_REQUEST);
//        } else {
//            throw new CommonException(INVALID_INPUT_ID,
//                    Messages.INVALID_OTP, StatusCodes.BAD_REQUEST);
//        }
//    }

    public BaseResponse validate(String otp, String mail) throws CommonException {

        OtpEntity otpData=otpRepo.findByMail(mail).orElse(new OtpEntity());
        if(otpData.getOtp().equals(otp)){
            otpRepo.delete(otpData);
            return new BaseResponse(VALID, Messages.YOUR_OTP_IS_VERIFIED);
        } else {
            throw new CommonException(INVALID_INPUT_ID,
                    Messages.INVALID_OTP, StatusCodes.BAD_REQUEST);
        }
    }
}
