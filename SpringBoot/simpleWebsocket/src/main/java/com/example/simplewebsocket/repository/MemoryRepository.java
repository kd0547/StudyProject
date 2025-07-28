package com.example.simplewebsocket.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MemoryRepository implements UsersRepository{
    // username → password (암호화된 값)

    private final Map<String, String> userStore = new ConcurrentHashMap<>();

    @Override
    public void save(String username, String password) {
        userStore.put(username, password);
        System.out.println("✅ 저장됨: " + username);
    }

    @Override
    public String find(String username ) {
        return userStore.get(username);
    }
}
