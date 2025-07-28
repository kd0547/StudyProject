package com.example.boardexample.dto;

import com.example.boardexample.entity.Comment;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class CommentDto {

    private Long id;

    private String author;

    private String comment;

    private LocalDateTime createAt;

    public CommentDto() {}
    public CommentDto(Long id,String author,String comment,LocalDateTime createAt) {
        this.id = id;
        this.comment = comment;
        this.author = author;
        this.createAt = createAt;
    }


}
