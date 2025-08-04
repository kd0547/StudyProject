package com.study.fileupload.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class FileManagementService {

    private static String uploadDir = "C:/temp/uploads/";



    @Transactional
    public void save(List<MultipartFile> files) throws Exception{
        try {
            for(var file : files) {

                File dest = new File(uploadDir+file.getOriginalFilename());
                file.transferTo(dest);
            }
        } catch (Exception e) {

        } finally {

        }

    }

}
