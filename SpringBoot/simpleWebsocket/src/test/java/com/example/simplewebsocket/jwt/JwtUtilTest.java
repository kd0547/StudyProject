package com.example.simplewebsocket.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JwtUtil.class)
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testGenerateAndExtractToken() {
        //given
        String username = "testuser";

        //when
        String token = jwtUtil.generateJwt(username);
        String extractedUsername = jwtUtil.extractUsername(token);

        // then
        assertEquals(username, extractedUsername);
        assertTrue(jwtUtil.isTokenValid(token));
    }
}
