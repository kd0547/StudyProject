package com.example.simplewebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;


@SpringBootApplication
public class SimpleWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleWebsocketApplication.class, args);
    }

}
