package com.example.boardexample.dto;

import lombok.Data;

@Data
public class SignupDto {

    private Long userId;
    private Long tokenId;

    public SignupDto() {}
    public SignupDto(Long userId, Long tokenId) {
        this.userId = userId;
        this.tokenId = tokenId;
    }
}
