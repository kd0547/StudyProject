package com.example.boardexample.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RetryTokenDto {

    private Long id;
    private String retryToken;

}
