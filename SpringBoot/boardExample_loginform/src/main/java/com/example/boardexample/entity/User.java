package com.example.boardexample.entity;

import com.example.boardexample.dto.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter @Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String address;

    private String imageUrl;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Orders> orders;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private boolean isActive = false;

    private LocalDateTime accessAt;

    private String status;


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "userCard_id")
    private UserCart userCart;


    public static User createUser(UserDto userDto) {
        User user = new User();
        user.setCreateAt(LocalDateTime.now());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }


}
