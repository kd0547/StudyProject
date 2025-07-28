package com.example.boardexample.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private double price;

    private int stock;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;


    public void reduceStock(int count) {
        if(this.stock < count) {
            throw new IllegalArgumentException("Insufficient stock for product ID: " + this.getId());
        }
        this.stock -= count;
    }

}
