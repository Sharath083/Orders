package com.task.orders.jwt;


import com.google.gson.Gson;
import com.task.orders.exception.CommonException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtHelper {
    static Gson gson = new Gson();

    public static final long JWT_TOKEN_VALIDITY = 24*24*60*60*1000 ;
    private final static String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    public SessionData getUsernameFromToken(String token) {
        return gson.fromJson(getClaimFromToken(token, Claims::getSubject), SessionData.class);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public UUID getUserId(){
        var token=(String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return UUID.fromString(getClaimFromToken(token, Claims::getId));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        System.out.println(expiration+"  --------expire");
        return expiration.before(new Date());
    }

    public static String generateToken(String id,String email,String name) {
        System.out.println(name+"in generateToken");
        Map<String, Object> claims = new HashMap<>();
        SessionData request = new SessionData();
        request.setUsername(name);
        request.setUserId(id);
        request.setEmail(email);
        return doGenerateToken(claims, request);
    }

    private static String doGenerateToken(Map<String, Object> claims, SessionData subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(gson.toJson(subject))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userMail = getUsernameFromToken(token).getEmail();
        if(isTokenExpired(token)){
            throw new CommonException("0001","Session has expired,please login again");
        }

        return (userMail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



}