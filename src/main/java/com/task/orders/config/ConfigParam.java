package com.task.orders.config;

import com.task.orders.constants.Constants;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ConfigParam {

    @Value(Constants.API_URL)
    private String apiUrl;
    @Value(Constants.API_BASE_URL)
    private String baseUrl;
    @Value(Constants.API_KEY)
    private String key;
    @Value(Constants.API_REQUEST_CODE)
    private String requestCode;
    @Value(Constants.API_USER_ID)
    private String userId;
    @Value(Constants.API_PASSWORD)
    private String password;
    @Value(Constants.TWILIO_ACCOUNT_SID)
    private String twilioId;
    @Value(Constants.TWILIO_AUTH_TOKEN)
    private String twilioToken;
    @Value(Constants.TWILIO_PHONE_NUMBER)
    private String twilioPhone;
    @Value(Constants.TWILIO_MESSAGE)
    private String message;
    @Value(Constants.MAIL_HOST)
    private String emailHost;
    @Value(Constants.MAIL_PORT)
    private String emailPort;
    @Value(Constants.MAIL_USERNAME)
    private String emailUsername;
    @Value(Constants.MAIL_PASSWORD)
    private String emailPassword;
    @Value(Constants.FRONTEND_ENDPOINT)
    private String frontendEndpoint;

//    @Value(Constants.REDIS_HOST)
//    private String redisHost;
//    @Value(Constants.REDIS_PORT)
//    private int redisPort;
//    @Value(Constants.REDIS_UNAME)
//    private String redisUName;
//    @Value(Constants.REDIS_PASSWORD)
//    private String redisPassword;
}
