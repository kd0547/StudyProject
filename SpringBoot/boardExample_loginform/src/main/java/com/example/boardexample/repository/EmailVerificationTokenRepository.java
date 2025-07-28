package com.example.boardexample.repository;

import com.example.boardexample.entity.EmailVerificationToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class EmailVerificationTokenRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(EmailVerificationToken emailVerificationToken) {
        em.persist(emailVerificationToken);
    }
}
