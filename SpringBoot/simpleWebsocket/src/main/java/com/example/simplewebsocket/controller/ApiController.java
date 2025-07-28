package com.example.simplewebsocket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/message")
    public String getMessage() {
        return "백엔드에서 온 메시지입니다!";
    }
    @GetMapping("/csrf-token")
    public CsrfToken csrf(CsrfToken token) {
        return token; // {headerName: "X-CSRF-TOKEN", parameterName: "_csrf", token: "abc123..."}
    }

    @PostMapping("/fileUpload")
    public ResponseEntity<String> handleFileUpload  (@RequestParam("file")MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            long size = file.getSize();

            return ResponseEntity.ok(filename + " (" + size + " bytes)");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 실패");
        }
    }
}
