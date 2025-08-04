package com.study.fileupload.dto;

import lombok.Data;

@Data
public class FileInfo {
    private TypeInfo typeInfo;
    private String name;
    private String size;
    private String modified;
}
