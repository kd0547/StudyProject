package com.study.fileupload.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RootPathCreator {

    public String createRootPath(String email) {
        String uuid = String.valueOf(UUID.randomUUID());
        log.info("[CREATE] user email: "+email+" :" +uuid);
        return uuid;
    }
}
