package com.example.boardexample.entity;

import com.example.boardexample.category.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Orders {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderNumber;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PREPARING;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    private List<OrdersDetails>  ordersDetails = new ArrayList<OrdersDetails>();

    public void addOrderDetails(OrdersDetails ordersDetails) {
        ordersDetails.setOrders(this);
        this.ordersDetails.add(ordersDetails);
    }
}
