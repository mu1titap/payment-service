package com.multitap.payment.api.kafka;

import com.multitap.payment.api.dto.in.PaymentInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, PaymentInfoDto> kafkaEditMentoringTemplate;

    /**
     * 멘토링 수정 이벤트 발생
     */
    public void sendUpdateMentoring(String topic, PaymentInfoDto dto) {
        log.info("send update dto :" + dto);
        try {
            kafkaEditMentoringTemplate.send(topic, dto);
        } catch (Exception e) {
            log.info("update payment event send 실패 : " + e);
            throw new RuntimeException(e);
        }
    }

}
