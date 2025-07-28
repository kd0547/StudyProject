package com.example.simplewebsocket.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간
    private SecretKey key;
    private final String secret = "1n-2310asfsf9n@9nU@5#@(0N!11S1F!91fAU0sS9nas5ISAddjfln$Wd9asa8sdsf7(2n3f5E1fskfE57c2719id3fskd9124*f4as25d";

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    public String generateJwt(String username) {
        String jwt = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() +  EXPIRATION_TIME))
                .signWith(key)
                .compact();

        return jwt;
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
