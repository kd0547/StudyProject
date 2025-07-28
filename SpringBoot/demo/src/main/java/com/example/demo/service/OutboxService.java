package com.example.demo.service;

import com.example.demo.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OutboxService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final OutboxRepository outboxRepository;


}
