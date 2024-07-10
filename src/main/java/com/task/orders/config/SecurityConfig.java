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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    ConfigParam configParam;
    @Autowired
    private ExceptionAuthenticationEntryPoint point;

    @Autowired
    private RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf().disable()
//                .authorizeHttpRequests(
//                        request->request
//                                .requestMatchers("/product/add", "/user/login", "/user/signup", "/thirdparty")
//                                .authenticated()
//                                .anyRequest().authenticated()
//                )
//                .exceptionHandling().authenticationEntryPoint(point)
//                .and()
//                .addFilterBefore(redisSessionAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/product/add", "/user/login", "/user/signup", "/thirdparty").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(point)
                .and()
                .addFilterBefore(redisSessionAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true); // Set to true if credentials are needed
//        configuration.addAllowedOrigin(configParam.frontendEndpoint); // Exact origin
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
