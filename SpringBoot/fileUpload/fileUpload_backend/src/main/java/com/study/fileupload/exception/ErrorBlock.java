package com.study.fileupload.exception;

import lombok.Data;

@Data
public class ErrorBlock {
    private ErrorCode errorCode;
    private String msg;

    public ErrorBlock() {
    }

    public ErrorBlock(ErrorCode errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
