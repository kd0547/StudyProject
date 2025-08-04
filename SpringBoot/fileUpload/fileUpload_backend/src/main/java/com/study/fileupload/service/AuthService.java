package com.study.fileupload.service;

import com.study.fileupload.entity.Users;
import com.study.fileupload.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users =  userRepository.findByEmail(username).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with username"));

        return new CustomUserDetails(
                users.getId(),
                users.getEmail(),
                users.getRoles()
        );
    }
}
