package com.example.boardexample.repository;


import com.example.boardexample.entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Orders order) {
        em.persist(order);
    }
}
