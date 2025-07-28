package com.example.simplewebsocket.repository;

public interface UsersRepository {

    void save(String username,String password);

    String find(String username);
}
