package com.study.fileupload.controller;

import ch.qos.logback.core.util.StringUtil;
import com.study.fileupload.dto.FileInfo;
import com.study.fileupload.dto.TypeInfo;
import com.study.fileupload.service.FileManagementService;
import com.study.fileupload.util.RootPathCreator;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/v1/file")
@RestController
@Slf4j
@RequiredArgsConstructor
public class FileManagementController {

    private final FileManagementService fileManagementService;


    @PostMapping("/upload")
    public void upload(Principal principal,
            @RequestParam("files") List<MultipartFile> files) throws Exception {
        log.info("[UPLOAD] user email: "+ principal.getName());
        isValidExtend(files);

        fileManagementService.save(files);

    }

    private void isValidExtend(List<MultipartFile> multipartFiles) throws IllegalArgumentException{
        for (MultipartFile file : multipartFiles) {
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            Assert.isTrue(
                    fileExtension != null && !"exe".equalsIgnoreCase(fileExtension),
                    "exe 파일 또는 확장자가 없는 파일은 업로드할 수 없습니다."
            );}
    }



    @PostMapping("/download")
    public void download(@RequestParam("fileId") String fileId) {

    }

    @DeleteMapping("/delete")
    public void delete() {}

    @GetMapping("/list")
    public ResponseEntity<?> list(Principal principal) {

        FileInfo fileInfo = new FileInfo();
        LocalDateTime localDateTime = LocalDateTime.now();
        fileInfo.setTypeInfo(TypeInfo.File);
        fileInfo.setName("이력서");
        fileInfo.setSize("14.2MB");
        fileInfo.setModified(localDateTime.toString());
        List<FileInfo> fileInfos = List.of(fileInfo);
        return ResponseEntity.ok().body(fileInfos);
    }

    public void rename() {}

    public void move() {}

    public void copy() {}

    @GetMapping("/info")
    public void getFileInfo() {}


}
