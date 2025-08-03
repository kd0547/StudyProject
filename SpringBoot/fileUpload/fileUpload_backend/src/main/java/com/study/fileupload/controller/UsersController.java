package com.study.fileupload.controller;


import com.study.fileupload.dto.SigninRequest;
import com.study.fileupload.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UsersController {

    private final UsersService usersService;

    @PostMapping("signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest){

        //중복 검사
        usersService.duplicate(signinRequest.getEmail());

        Long saveId = usersService.save(signinRequest);
        return ResponseEntity.ok(saveId);
    }
}
