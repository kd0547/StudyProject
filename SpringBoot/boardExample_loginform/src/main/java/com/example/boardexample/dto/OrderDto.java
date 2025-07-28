package com.example.boardexample.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long memberId;
    private List<ProductDto> productDtos;
}
