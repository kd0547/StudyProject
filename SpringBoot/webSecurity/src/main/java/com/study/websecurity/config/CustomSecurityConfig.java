package com.study.websecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Slf4j
public class CustomSecurityConfig extends AbstractHttpConfigurer<CustomSecurityConfig, HttpSecurity> {

    private boolean isSecure = true;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
        log.info("configure custom security config started...");
    }

    @Override
    public void init(HttpSecurity builder) throws Exception {
        super.init(builder);
        log.info("init custom security config started...");
        if(isSecure) {
            log.info("https is required");
        } else  {
            log.info("https is optional");
        }
    }

    public CustomSecurityConfig SetIsSecure(boolean isSecure) {
        this.isSecure = isSecure;
        return this;
    }
}
