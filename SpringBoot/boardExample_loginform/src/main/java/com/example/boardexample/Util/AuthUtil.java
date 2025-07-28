package com.example.boardexample.Util;

import java.util.UUID;

public class AuthUtil {

    public String createToekn() {
        return UUID.randomUUID().toString();
    }
}
