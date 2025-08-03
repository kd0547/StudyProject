package com.study.fileupload.entity;

import com.study.fileupload.dto.SigninRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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


    public static Users create(SigninRequest signinRequest) {
        Users users = new Users();
        users.setEmail(signinRequest.getEmail());
        users.setPassword(signinRequest.getPassword());
        return users;
    }
}
