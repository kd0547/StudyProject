package com.example.demo.dto;

import com.example.demo.Entity.Orders;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.Order;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderViewDto {
    private Long userId;

    private List<OrderDto> orders = new ArrayList<OrderDto>();

    public void addOrder(Orders orders) {

        this.orders.add(
                OrderDto.createOrderDto(orders.getId(), orders.getProduct())
        );
    }

    @Getter
    @Setter
    public static class OrderDto {
        private Long orderId;
        private String product;

        public OrderDto(Long orderId, String product) {
            this.orderId = orderId;
            this.product = product;
        }

        public static OrderDto createOrderDto(Long orderId, String product) {
            return new OrderDto(orderId,product);
        }

    }
}
