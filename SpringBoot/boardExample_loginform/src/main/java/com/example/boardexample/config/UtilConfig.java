package com.example.boardexample.config;

import com.example.boardexample.Util.AuthUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {

    @Bean
    public AuthUtil createUtil() {
        return new AuthUtil();
    }
}
