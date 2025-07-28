package com.example.outboxservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OutboxServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutboxServiceApplication.class, args);
    }

}
