package com.task.orders.config;



import com.task.orders.exception.CommonException;
import com.task.orders.jwt.JwtHelper;
import com.task.orders.jwt.SessionData;
import com.task.orders.service.CustomService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    JwtHelper jwtHelper;
    @Autowired
    CustomService userService;

    public static String token=null;
    @Getter
    private static SessionData sessionData=null;

    @Override
    public void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        String username = null;
        System.out.println(requestHeader+" ----------------------------");
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            try {
                sessionData =this.jwtHelper.getUsernameFromToken(token);
                username = sessionData.getUsername();
            }catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
            }catch (Exception e) {
                throw new CommonException("00",e.getMessage());
            }
        } else {
            logger.info("Invalid Header Value !!");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userService.loadUserByUsername(sessionData.getEmail());
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("Validation fails !!");
                throw new CommonException("0001","Session has expired,please login again");

            }
        }
        filterChain.doFilter(request, response);
    }


}