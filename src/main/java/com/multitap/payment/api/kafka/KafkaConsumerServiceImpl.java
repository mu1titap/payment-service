package com.multitap.payment.api.kafka;

import com.multitap.payment.api.infrastructure.VoltHistoryRepository;
import com.multitap.payment.api.kafka.messageIn.SessionConfirmedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final VoltHistoryRepository voltHistoryRepository;

    @Override
    @Transactional
    public void updateVoltHistoryStatus(SessionConfirmedDto sessionConfirmedDto) {

        voltHistoryRepository.findBySessionUuid(sessionConfirmedDto.getSessionUuid())
            .ifPresentOrElse(
                voltHistory -> {
                    voltHistory.forEach(v -> {
                        v.updatePaymentStatus(sessionConfirmedDto);
                        voltHistoryRepository.save(v);
                    });

                },
                () -> log.error("VoltHistory not found for sessionUuid: {}",
                    sessionConfirmedDto.getSessionUuid())
            );

        log.info("sessionConfirmedDto: {}", sessionConfirmedDto.toString());


    }

}
