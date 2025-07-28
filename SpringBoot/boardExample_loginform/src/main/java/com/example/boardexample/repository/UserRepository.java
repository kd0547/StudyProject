package com.example.boardexample.repository;

import com.example.boardexample.entity.EmailVerificationToken;
import com.example.boardexample.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<User> findUserById(Long id) {
        User findUser = em.find(User.class,id);

        return Optional.ofNullable(findUser);
    }

    public User selectUserProduct(Long id) {
        User userProxy = em.getReference(User.class,id);

        return userProxy;
    }

    public void save(User user) {
        em.persist(user);
    }


    public EmailVerificationToken findRetryToken(Long id) {
        EmailVerificationToken emailVerificationToken = em.createQuery("select t from EmailVerificationToken t " +
                        "where t.user.id = :userId", EmailVerificationToken.class)
                .setParameter("userId",id)
                .getSingleResult();

        return emailVerificationToken;

    }
}
