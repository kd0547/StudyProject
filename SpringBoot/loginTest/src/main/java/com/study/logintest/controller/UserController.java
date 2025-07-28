package com.study.logintest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {




    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }
}
