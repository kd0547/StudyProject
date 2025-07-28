package com.example.boardexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BoardResponseDto {
    private Long boardCount;
    private List<BoardDto> boards;
}
