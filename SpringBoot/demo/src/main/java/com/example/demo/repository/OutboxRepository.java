package com.example.demo.repository;

import com.example.demo.Entity.Orders;
import com.example.demo.Entity.Outbox;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OutboxRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Orders orders) {
        Outbox outbox = new Outbox(orders);

        em.persist(outbox);

        return outbox.getId();
    }
}
