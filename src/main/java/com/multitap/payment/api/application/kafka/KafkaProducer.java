package com.multitap.payment.api.application.kafka;

import com.multitap.payment.api.dto.out.PaySessionRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, PaySessionRequestDto> kafkaPaySessionTemplate;

    public void sendPaySessionMessage(String topic, PaySessionRequestDto dto) {
        try {
            kafkaPaySessionTemplate.send(topic, dto);
        } catch (Exception e) {
            log.info("sendPaySessionMessage 실패 : " + e);
            throw new RuntimeException(e);
        }
    }

}
