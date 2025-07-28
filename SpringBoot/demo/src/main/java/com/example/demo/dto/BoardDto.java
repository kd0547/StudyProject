package com.example.demo.dto;

import com.example.demo.Entity.Board;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private Long id;

    private String title;

    private String subject;

    private String author;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public static BoardDto createDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setAuthor(board.getAuthor());
        boardDto.setSubject(board.getSubject());
        boardDto.setCreateAt(board.getCreateAt());
        boardDto.setUpdateAt(board.getUpdateAt());

        return boardDto;
    }
}
