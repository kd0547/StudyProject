package com.example.boardexample.controller;

import com.example.boardexample.dto.RetryTokenDto;
import com.example.boardexample.dto.SignupDto;
import com.example.boardexample.dto.UserDto;
import com.example.boardexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/user/new")
    public ResponseEntity<Object> signup(@RequestBody UserDto signupDto) {
        SignupDto result =  userService.signup(signupDto);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/user/retry")
    public void retry(@RequestBody RetryTokenDto retryTokenDto) {
        userService.retry(retryTokenDto);
    }
}
