package com.example.demo.repository;


import com.example.demo.Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UserRepository {


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long save(String username) {
        User user = new User(username);

        em.persist(user);
        em.flush();

        return user.getId();
    }


    public Optional<User> findUser(Long id) {
        User findUser = em.find(User.class,id);
        return Optional.ofNullable(findUser);
    }
}
