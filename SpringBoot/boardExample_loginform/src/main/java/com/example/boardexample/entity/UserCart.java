package com.example.boardexample.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserCart {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany (cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<Product>();

    
}
