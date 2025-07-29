package com.study.fileupload.repository;

import com.study.fileupload.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    public Optional<Users> findByEmail(String username) {
        return null;
    }
}
