package com.study.fileupload.jwt;

import com.study.fileupload.service.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간
    private final SecretKey key;




    public String generateJwt(Authentication authentication) {
        String username = authentication.getName();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);


        return Jwts.builder().subject(username).issuedAt(now)
                .claim("userId",userId)
                .claim("roles",authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String getUsername(String jwt) {


        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

    public List<String> getRole(String jwt) {

    }

    public boolean isExpired(String jwt) throws RuntimeException{
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getExpiration()
                .after(new Date());
    }

    public List<GrantedAuthority> getAuthorities(String authorization) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(authorization)
                .getPayload();
        List<?> roles = claims.get("roles",List.class);
        if (roles == null)
            return Collections.emptyList();

        return roles.stream()
                .map(Object::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
