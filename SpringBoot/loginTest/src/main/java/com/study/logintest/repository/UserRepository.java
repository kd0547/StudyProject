package com.study.logintest.repository;

import com.study.logintest.Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<User> getUserByUsername(String username) {
        User user = em.createQuery("select u from User u where u.username = :username",User.class)
                .setParameter("username", username)
                .getSingleResult();
        return Optional.ofNullable(user);
    }

    public void save(User user) {
        em.persist(user);
    }
}
