package com.example.outboxservice.service;

import com.example.outboxservice.entity.Outbox;
import com.example.outboxservice.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxProcessor {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final OutboxRepository outboxRepository;

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void processOutboxMessages() {
        log.info("processOutboxMessages() 실행됨");
        List<Outbox> list = outboxRepository.findByStatus();
        if(list.size() == 0) {
            return;
        }

        list.stream()
                .forEach(outbox -> {
                    try {
                        kafkaTemplate.send("order-topic", outbox.getPayload()); // 동기 전송
                    } catch (Exception e)
                    {
                        log.info("kafka Send 결과 : FAILED");
                        outbox.setStatus("FAILED");
                    }
                    outbox.setStatus("SENT"); //전송완료
                });

    }
}
