package com.example.boardexample.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailVerificationPayload {
    private String email;
    private String token;
    private LocalDateTime expiredAt;

    public EmailVerificationPayload() {}
    public EmailVerificationPayload(String email, String token, LocalDateTime expiredAt) {
        this.email = email;
        this.token = token;
        this.expiredAt = expiredAt;
    }

}
