package com.example.demo.service;

import com.example.demo.Entity.HelloEntity;
import com.example.demo.repository.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HelloService {

    @Autowired
    private HelloRepository helloRepository;

    @Transactional
    public String hello(Long id)
    {
        return helloRepository.FindData(id)
                .map(HelloEntity::getMessage)
                .orElse("not Found Data");
    }

    @Transactional
    public Long saveData(String message)
    {
        return helloRepository.saveData(message);
    }


}
