package com.study.boardexample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Board {

    @Id @GeneratedValue(strategy =  GenerationType.AUTO)
    private int id;

    private String title;

    private String content;

    private String author;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}
