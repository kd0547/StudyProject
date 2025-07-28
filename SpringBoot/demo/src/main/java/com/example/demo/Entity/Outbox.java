package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long aggregateId;

    private String eventType;

    private String payload;

    private String status = "PENDING";

    public Outbox() {}
    public Outbox(Orders orders) {
        this.aggregateId = orders.getId();
        this.payload = orders.getAddress().getStreet();
        this.eventType = "ORDER_CREATED";
    }
}
