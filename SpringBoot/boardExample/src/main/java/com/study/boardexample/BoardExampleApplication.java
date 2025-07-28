package com.study.boardexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class BoardExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardExampleApplication.class, args);
    }

}
