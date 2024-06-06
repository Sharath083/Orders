
package com.task.orders.redis;

import com.task.orders.dto.SessionData;
import com.task.orders.exception.CommonException;
import com.task.orders.helpers.Constants;
import com.task.orders.helpers.Crypto;
import com.task.orders.service.CustomStudentService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RedisSessionAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisHelper redisHelper;

    @Autowired
    private Crypto crypto;

    @Autowired
    private CustomStudentService userService;

    private static SessionData sessionData = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("---------------- "+request.getInputStream().toString());
        String sessionId = request.getHeader("session-id");
        if (sessionId != null) {
            try {
                sessionData = validateSession(sessionId);
                UserDetails userDetails = userService.loadUserByUsername(sessionData.getEmail());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new AuthenticationException(e.getMessage()){};
            }
        }


        filterChain.doFilter(request, response);
    }

    private SessionData validateSession(String session) throws AuthenticationException {
        try {
            String[] data = crypto.decrypt(session).split("//");
            String key = Constants.REDIS_KEY + data[0];
            String value = redisHelper.get(key);
            if (value == null) {
                throw new AuthenticationException("Session Expired") {};
            } else if (!value.equals(session)) {
                throw new AuthenticationException("Invalid session key") {};
            }
            return new SessionData(data[0], data[1],data[2]);
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("Invalid session key") {};
        }
    }

    public static SessionData getUserData() {
        return sessionData;
    }
}
