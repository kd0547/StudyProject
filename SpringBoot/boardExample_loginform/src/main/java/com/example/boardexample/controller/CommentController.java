package com.example.boardexample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {


    @PostMapping("/comment/new")
    public ResponseEntity<String> post() {



        return ResponseEntity.ok("");
    }
}
