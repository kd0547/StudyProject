package com.example.demo.service;

import com.example.demo.Entity.Orders;
import com.example.demo.Entity.User;
import com.example.demo.dto.OrderViewDto;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OutboxRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OutboxRepository outboxRepository;

    private final KafkaTemplate<String,String> kafkaTemplate;
    private static final String TOPIC = "order-topic";  // 토픽명


    @Transactional
    public Long add(Long id, String product, String address) {
        User user = userRepository.findUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Error"));

        Orders orders = orderRepository.save(user,product,address);

        outboxRepository.save(orders);

        return orders.getId();
    }


    public OrderViewDto orderView(Long id) {

        List<Orders> orders = orderRepository.findOrders(id);

        OrderViewDto orderViewDto = new OrderViewDto();
        orderViewDto.setUserId(id);
        orders.forEach(orderViewDto::addOrder);

        return orderViewDto;
    }
}
