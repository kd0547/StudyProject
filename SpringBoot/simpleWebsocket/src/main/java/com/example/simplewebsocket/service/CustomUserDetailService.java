package com.example.simplewebsocket.service;

import com.example.simplewebsocket.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String encodePassword = usersRepository.find(username);
        if(encodePassword == null) {
            throw new UsernameNotFoundException("이메일이 없습니다.");
        }


        return User.builder()
                .username(username)
                .password(encodePassword)
                .roles("USER")
                .build();
    }
}
