package com.task.orders.service.impl.mail;

import com.task.orders.config.ConfigParam;
import com.task.orders.helpers.HelperFunctions;
import com.task.orders.redis.RedisHelper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.task.orders.helpers.Constants.*;

@Service
public class EmailGenerator {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    private ConfigParam configParam;
    @Autowired
    RedisHelper redisHelper;
    @Autowired
    private SimpleMailMessage simpleMailMessage;
    public String generateEmail(String mail) {
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject(ORDER_DETAILS);
        simpleMailMessage.setText("Sample Mail");

        mailSender.send(simpleMailMessage);
        return "Sample Mail sent successfully";
    }
    public String sendOtp(String mail,String userId) {
        String otp=HelperFunctions.generateOtp();
        String msg=configParam.getMessage().replace("?",otp);
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject(OTP_SUBJECT);
        simpleMailMessage.setText(msg);

        mailSender.send(simpleMailMessage);
        redisHelper.set(OTP_REDIS_KEY+userId,otp,5);

        return "Sample Mail sent successfully";
    }
}
