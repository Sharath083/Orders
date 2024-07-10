
package com.task.orders.config;


import com.task.orders.constants.Constants;
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
    public
    RedisHelper redisHelper;
    @Autowired
    ConfigParam configParam;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public SimpleCORSFilter filter() {
//        return new SimpleCORSFilter();
//    }
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
        props.put(Constants.MAIL_TRANSPORT_PROTOCOL, Constants.SMTP);
        props.put(Constants.MAIL_SMTP_AUTH, Constants.TRUE);
        props.put(Constants.MAIL_SMTP_STARTTLS_ENABLE, Constants.TRUE);
        props.put(Constants.MAIL_SMTP_CONNECTIONTIMEOUT, Constants.TIMEOUT);
        props.put(Constants.MAIL_SMTP_TIMEOUT, Constants.TIMEOUT);
        props.put(Constants.MAIL_SMTP_WRITETIMEOUT, Constants.TIMEOUT);
        props.put(Constants.MAIL_DEBUG, Constants.TRUE);

        return mailSender;
    }

    @Bean
    public SimpleMailMessage mailSender(){
        return new SimpleMailMessage();
    }

    //otp twilio client
    public void twilioConnect(String id,String token){
        Twilio.init(id,token);
    }
}
