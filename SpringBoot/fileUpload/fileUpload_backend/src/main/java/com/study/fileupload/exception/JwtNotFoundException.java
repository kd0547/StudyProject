package com.study.fileupload.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtNotFoundException extends AuthenticationException {
    public JwtNotFoundException(String message) {
        super(message);
    }
}
