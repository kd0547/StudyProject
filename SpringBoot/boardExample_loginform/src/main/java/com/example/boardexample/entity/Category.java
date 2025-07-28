package com.example.boardexample.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int categoryCode;

    //상위 카테고리를 가리키는 필드 (부모 카테고리)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> children = new ArrayList<>();


    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> products;



}
