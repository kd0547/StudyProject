package com.example.outboxservice.repository;

import com.example.outboxservice.entity.Outbox;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OutboxRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Outbox> findByStatus() {
        return em.createQuery("select o from Outbox o WHERE status = 'PENDING'",Outbox.class)
                .getResultList();
    }
}
