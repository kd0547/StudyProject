package com.study.fileupload.entity;

import com.study.fileupload.dto.SigninRequest;
import com.study.fileupload.service.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Users {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    private String rootPath;

    public static Users create(SigninRequest signinRequest,String rootPath) {
        Users users = create(signinRequest);
        users.setRootPath(rootPath);
        return users;
    }

    public static Users create(SigninRequest signinRequest) {
        Users users = new Users();
        users.setEmail(signinRequest.getEmail());
        users.setPassword(signinRequest.getPassword());
        return users;
    }
}
