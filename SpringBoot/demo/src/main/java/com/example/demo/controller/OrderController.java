package com.example.demo.controller;

import com.example.demo.Entity.Orders;
import com.example.demo.dto.OrderViewDto;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;



    @PostMapping("/add")
    public ResponseEntity<Object> order(@RequestBody Map<String,Object> body) {
        Long id = ((Number) body.get("userId")).longValue();
        String product = (String) body.get("product");
        String address = (String) body.get("address");

        Long saveId = orderService.add(id,product,address);

        return ResponseEntity.ok(new Object() {
            public final Long id = saveId;
        });
    }

    @GetMapping("/view")
    public ResponseEntity<OrderViewDto> orderView(@RequestBody Map<String,Object> body) {
        Long id = ((Number)body.get("userId")).longValue();

        OrderViewDto orderViewDto = orderService.orderView(id);
        return ResponseEntity.ok(orderViewDto);
    }
}
