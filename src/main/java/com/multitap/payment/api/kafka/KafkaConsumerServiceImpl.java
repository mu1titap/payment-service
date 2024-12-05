package com.multitap.payment.api.kafka;

import com.multitap.payment.api.infrastructure.VoltHistoryRepository;
import com.multitap.payment.api.kafka.messageIn.SessionConfirmedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final VoltHistoryRepository voltHistoryRepository;

    @Override
    public void updateVoltHistoryStatus(SessionConfirmedDto sessionConfirmedDto) {

        voltHistoryRepository.findBySessionUuid(sessionConfirmedDto.getSessionUuid())
            .ifPresentOrElse(
                voltHistory -> {
                    voltHistory.setSessionStatus(sessionConfirmedDto.getSessionStatus());
                    voltHistoryRepository.save(voltHistory);
                },
                () -> log.error("VoltHistory not found for sessionUuid: {}",
                    sessionConfirmedDto.getSessionUuid())
            );

        log.info("sessionConfirmedDto: {}", sessionConfirmedDto.toString());


    }

}
