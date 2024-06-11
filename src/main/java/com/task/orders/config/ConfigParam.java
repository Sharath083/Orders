package com.task.orders.config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Getter
public class ConfigParam {
    @Value("${api.url}")
    private String apiUrl;
    @Value("${api.baseUrl}")
    private String baseUrl;
    @Value("${api.key}")
    private String key;
    @Value("${api.requestCode}")
    private String requestCode;
    @Value("${api.UserId}")
    private String userId;
    @Value("${api.password}")
    private String password;
}
