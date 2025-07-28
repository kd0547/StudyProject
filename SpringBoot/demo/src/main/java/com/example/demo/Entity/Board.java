package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Setter @Getter
@Entity
@Table(name = "board",indexes = {
        @Index(name = "board_index_page",columnList = "createAt, id")
})
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String subject;

    private String author;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;


}
