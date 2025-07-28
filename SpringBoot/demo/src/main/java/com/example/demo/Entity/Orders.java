package com.example.demo.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Orders {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;


    public Orders() {}
    public Orders(String product) {
        this.product = product;
    }

    public Orders(User user, String product, Address saveAddress) {
        this.user = user;
        this.product = product;
        this.address = saveAddress;
    }
}
