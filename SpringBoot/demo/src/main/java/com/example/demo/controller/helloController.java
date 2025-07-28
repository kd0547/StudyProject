package com.example.demo.controller;

import com.example.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @Autowired private HelloService helloService;

    @GetMapping(value = "/hello")
    public ResponseEntity<String> Hello()
    {
        return ResponseEntity.ok("Hello docker file");
    }

    @GetMapping(value = "/message/{message}")
    public ResponseEntity<Long> inputData(@PathVariable String message)
    {
        return ResponseEntity.ok(
                helloService.saveData(message)
        );
    }

    @GetMapping(value = "/data/{id}")
    public ResponseEntity<String> outputData(@PathVariable Long id)
    {
        String message = helloService.hello(id);
        return ResponseEntity.ok(message);
    }

}
