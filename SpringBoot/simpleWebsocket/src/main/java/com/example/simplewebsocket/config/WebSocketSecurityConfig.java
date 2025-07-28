package com.example.simplewebsocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager.Builder;

//@Configuration
//@EnableWebSocketSecurity
public class WebSocketSecurityConfig {

    @Bean
    AuthorizationManager<Message<?>> authorizationManager(Builder messages) {
        messages.simpDestMatchers("/user/queue/errors").permitAll()
                .simpDestMatchers("/admin/**").hasRole("ADMIN")
                .anyMessage().authenticated();
        return messages.build();
    }
}
