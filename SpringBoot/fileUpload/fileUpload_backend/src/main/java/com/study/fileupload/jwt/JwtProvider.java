package com.study.fileupload.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간
    private final Key key;




    public String generateJwt(Authentication authentication) {
        String username = authentication.getName();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String getUsername(String jwt) {
        return "user";
    }

    public String getRole(String jwt) {
        return "ROLE_USER";
    }

    public boolean isExpired(String jwt) throws RuntimeException{
        if (jwt == null)
            throw new RuntimeException("JWT is null");



        return false;
    }

    public boolean isAuthenticated(String jwt) {
        return true;
    }

    public List<GrantedAuthority> getAuthorities(String authorization) {
        return null;
    }
}
