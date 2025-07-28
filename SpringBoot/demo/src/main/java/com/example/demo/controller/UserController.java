package com.example.demo.controller;

import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Map<String, Object> body)
    {
        String username = (String)body.get("username");

        Long id = userService.add(username);

        return ResponseEntity.ok(new Object() {
            public final Long userId = id;
            public final String message = "User added successfully";
        });
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<Object> view(@PathVariable Long id)
    {

        String findUser = userService.view(id);

        return ResponseEntity.ok(new Object(){
            public final String username = findUser;
        });
    }

}
