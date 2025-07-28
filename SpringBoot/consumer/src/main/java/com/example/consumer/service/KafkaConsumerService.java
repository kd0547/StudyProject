package com.example.consumer.service;

import com.example.consumer.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final DeliveryRepository deliveryRepository;

    @KafkaListener(topics = "order-topic",groupId = "order-group")
    @Transactional
    public void consume(String message) {
        log.info("데이터 받아옴");
        deliveryRepository.save(message);
        log.info("데이터 저장 완료");

    }
}
