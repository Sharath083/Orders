
package com.task.orders.cache_Redis;

import com.task.orders.constants.InfoId;
import com.task.orders.constants.Messages;
import com.task.orders.constants.StatusCodes;
import com.task.orders.dto.SessionData;
import com.task.orders.constants.Constants;
import com.task.orders.exception.CommonException;
import com.task.orders.helpers.Crypto;
import com.task.orders.service.CustomService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

//@Component
public class RedisSessionAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisHelper redisHelper;

    @Autowired
    private Crypto crypto;

    @Autowired
    private CustomService userService;

    private static SessionData sessionData = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        String sessionId = request.getHeader(Constants.SESSION_ID);
        if (sessionId != null) {
            try {
                sessionData = validateSession(sessionId);
                UserDetails userDetails = userService.loadUserByUsername(sessionData.getEmail());
                if (!Objects.equals(userDetails.getUsername(), sessionData.getEmail())) {
                    throw new AuthenticationException(Messages.SESSION_IS_INVALID) {
                    };
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new CommonException(InfoId.INVALID_INPUT_ID,
                        e.getMessage(), StatusCodes.UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request, response);
    }

    private SessionData validateSession(String session) throws AuthenticationException {
        try {
            String[] data = Crypto.decrypt(session).split("//");
            String key = Constants.REDIS_KEY + data[0];
            String value = redisHelper.get(key);
            if (value == null) {
                throw new CommonException(InfoId.INVALID_INPUT_ID, Messages.SESSION_EXPIRED, StatusCodes.UNAUTHORIZED);

            } else if (value.equals(session)) {
                return new SessionData(data[0], data[1], data[2]);
            } else {
                throw new CommonException(InfoId.INVALID_INPUT_ID,
                        Messages.INVALID_SESSION_KEY, StatusCodes.UNAUTHORIZED);
            }
        } catch (IllegalArgumentException e) {
            throw new CommonException(InfoId.INVALID_INPUT_ID,
                    e.getMessage(), StatusCodes.UNAUTHORIZED);
        }
    }

    public SessionData getUserData() {
        return sessionData;
    }
}
