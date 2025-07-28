package com.example.boardexample.entity;

import com.example.boardexample.dto.CommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
public class Comment {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String comment;

    private String author;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public CommentDto CreateCommentDto() {
        return new CommentDto(
                this.getId(),
                this.author,
                this.comment,
                this.createAt
        );
    }

}
