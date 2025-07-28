package com.example.simplewebsocket.config;

import com.example.simplewebsocket.repository.MemoryRepository;
import com.example.simplewebsocket.repository.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public UsersRepository usersRepository() {
        return new MemoryRepository();
    }
}
