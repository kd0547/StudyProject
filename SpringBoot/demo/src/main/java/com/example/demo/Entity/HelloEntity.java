package com.example.demo.Entity;

import com.example.demo.service.HelloService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class HelloEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long HelloId;
    private String message;

    public HelloEntity(String message) {
        this.message = message;
    }

    public HelloEntity() {

    }
}
