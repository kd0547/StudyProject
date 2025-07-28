package com.example.simplewebsocket.service;

import com.example.simplewebsocket.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        usersRepository.save(username,encodedPassword);
    }
}
