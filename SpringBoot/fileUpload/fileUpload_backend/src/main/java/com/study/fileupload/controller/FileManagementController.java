package com.study.fileupload.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/file")
@RestController
public class FileManagementController {

    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        //파일 저장 로직

    }

    @PostMapping("/download")
    public void download(@RequestParam("fileId") String fileId) {

    }

    @DeleteMapping("/delete")
    public void delete() {}

    @GetMapping("/list")
    public ResponseEntity<String> list() {
        return ResponseEntity.ok().body("HELLO WORLD");
    }

    public void rename() {}

    public void move() {}

    public void copy() {}

    @GetMapping("/info")
    public void getFileInfo() {}


}
