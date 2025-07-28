package com.example.demo.repository;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.Orders;
import com.example.demo.Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;


    public Orders save(User user, String product, String address) {
        Address saveAddress = new Address();
        saveAddress.setStreet(address);

        Orders orders = new Orders(user,product,saveAddress);
        saveAddress.setOrders(orders);

        em.persist(orders);

        return orders;
    }

    @Transactional
    public List<Orders> findOrders(Long id) {
        return em.createQuery("select o from Orders o where  o.user.id = :id",Orders.class)
                .setParameter("id",id)
                .getResultList();
    }
}
