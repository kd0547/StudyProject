package com.example.boardexample.controller;

import com.example.boardexample.dto.OrderDto;
import com.example.boardexample.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/test/order/new")
    public ResponseEntity<Map<String,Object>> testCreateOrder(@RequestBody OrderDto orderDto) {
        long start = System.currentTimeMillis();

        Long id = orderService.OrderSave(orderDto.getMemberId(),orderDto.getProductDtos());
        long end = System.currentTimeMillis();
        log.info("⏱ DB 처리 소요 시간(ms): " + (end - start));
        Map<String,Object> responseJson = new HashMap<>();
        responseJson.put("id",id);
        return ResponseEntity.ok(responseJson);
    }
}
