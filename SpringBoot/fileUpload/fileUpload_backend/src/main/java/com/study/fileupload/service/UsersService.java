package com.study.fileupload.service;

import com.study.fileupload.dto.SigninRequest;
import com.study.fileupload.entity.Users;
import com.study.fileupload.exception.DuplicateEmailException;
import com.study.fileupload.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long save(SigninRequest signinRequest) {
        String encodedPassword = bCryptPasswordEncoder.encode(signinRequest.getPassword());
        signinRequest.setPassword(encodedPassword);
        Users users= userRepository.save(Users.create(signinRequest));
        
        //이메일 전송까지만 보호 재전송 로직 추가
        //이메일 전송 로직 추가
        
        return users.getId();
    }

    public void duplicate(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        });
    }
}
