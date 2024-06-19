
package com.task.orders.config;


import com.task.orders.helpers.Constants;
import com.task.orders.helpers.Crypto;
import com.task.orders.redis.RedisHelper;


import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class MyConfig {
    @Autowired
    private Crypto crypto;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private ConfigParam configParam;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configParam.getEmailHost());
        mailSender.setPort(Integer.parseInt(configParam.getEmailPort()));

        mailSender.setUsername(configParam.getEmailUsername());
        mailSender.setPassword(configParam.getEmailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.writetimeout", "5000");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public SimpleMailMessage mailSender(){
        return new SimpleMailMessage();
    }
    public String generateRedisToken(String id, String email,String name) {
        var key=crypto.encrypt(id+"//"+email+"//"+name);
        redisHelper.set(Constants.REDIS_KEY+id,key);
        return key;
    }

    //otp twilio client
        public void twilioConnect(String id,String token){
        Twilio.init(id,token);
    }
}
