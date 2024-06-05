
package com.task.orders.config;

import com.task.orders.exception.ExceptionAuthenticationEntryPoint;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private ExceptionAuthenticationEntryPoint point;

    @Autowired
    private RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .requestMatchers("/product/add","/user/login", "/user/signup").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(point)
                .and()
                .addFilterBefore(redisSessionAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
