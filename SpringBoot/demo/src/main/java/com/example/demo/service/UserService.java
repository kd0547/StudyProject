package com.example.demo.service;

import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long add(String username) {
        return userRepository.save(username);
    }

    @Transactional
    public String view(Long id) {

        return  userRepository.findUser(id)
                .map(User::getName)
                .orElseThrow();
    }
}
