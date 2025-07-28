package com.example.consumer.repository;

import com.example.consumer.Entity.Delivery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(String message) {
        Delivery delivery = new Delivery();
        delivery.setStreet(message);
        em.persist(delivery);
    }

}
