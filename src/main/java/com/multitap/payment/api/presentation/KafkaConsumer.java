package com.multitap.payment.api.presentation;

import com.multitap.payment.api.kafka.KafkaConsumerService;
import com.multitap.payment.api.kafka.messageIn.SessionConfirmedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaConsumerService kafkaConsumerService;

    @KafkaListener(topics = "update-session-confirmed", groupId = "payment-consumer-group", containerFactory = "sessionConfirmedDtoListener")
    public void updateVoltHistoryStatus(SessionConfirmedDto sessionConfirmedDto) {
        log.info("sessionConfirmedDto message: {}", sessionConfirmedDto);
        kafkaConsumerService.updateVoltHistoryStatus(sessionConfirmedDto);
        log.info("after  message: {}", sessionConfirmedDto);
    }

}
