package com.example.demo.repository;

import com.example.demo.Entity.HelloEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HelloRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<HelloEntity> FindData(Long id)
    {
        HelloEntity helloEntity = em.find(HelloEntity.class,id);

        return Optional.ofNullable(helloEntity);
    }

    public Long saveData(String message) {
        HelloEntity entity = new HelloEntity(message);
        entity.setMessage(message);

        em.persist(entity);
        em.flush();

        return entity.getHelloId();
    }
}
