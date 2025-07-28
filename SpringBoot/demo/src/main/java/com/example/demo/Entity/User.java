package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@NamedEntityGraph(name = "User.withOrders", attributeNodes = @NamedAttributeNode("orders"))
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Orders> orders;
    public User() {

    }

    public User(String username) {
        this.name = username;
    }
}
