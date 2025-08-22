package com.devcode.ecommerce.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    private static final String secretKey = "b/7tKxJc6GTh2cHHFPYUNfyGGtvlyC4/RRV5gXOccDs=";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));

    public JWTService() {

    }

    public static String extractUserName(String token) {
        return getClaims(token).getSubject();
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        Date expirationTime = extractExpiration(token);
        if (expirationTime.before(new Date())) {
            throw new RuntimeException("Token has expired");
        }
        return userName.equals(userDetails.getUsername());
    }

    public String generateJWTToken(String username, String password) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .and()
                .signWith(getJWTKey())
                .compact();
    }

    private Key getJWTKey() {
        byte[] secretKeyBytes = java.util.Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

    private static Date extractExpiration(String token) {
        return getClaims(token)
                .getExpiration();

    }
}
