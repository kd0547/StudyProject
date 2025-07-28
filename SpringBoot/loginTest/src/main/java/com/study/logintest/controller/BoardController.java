package com.study.logintest.controller;


import com.study.logintest.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    public String view() {

        return "board/view";
    }
}
