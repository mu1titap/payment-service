package com.multitap.payment.api.presentation;

import com.multitap.payment.api.infrastructure.VoltHistoryRepository;
import com.multitap.payment.api.kafka.messageIn.SessionConfirmedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final VoltHistoryRepository voltHistoryRepository;

    
    public void updateVoltHistoryStatus(SessionConfirmedDto sessionConfirmedDto) {
        log.info("sessionConfirmedDto message: {}", sessionConfirmedDto);
        voltHistoryRepository.save(message);
    }

}
