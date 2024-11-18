package com.multitap.payment.api.kafka;

import com.multitap.payment.api.dto.in.UserReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, UserReqDto> kafkaEditMentoringTemplate;

    /**
     * 멘토링 수정 이벤트 발생
     */
    public void sendPaymentInfo(String topic, UserReqDto dto) {
        log.info("send PaymentInfo:" + dto);
        try {
            kafkaEditMentoringTemplate.send(topic, dto);
        } catch (Exception e) {
            log.info("payment event send 실패 : " + e);
            throw new RuntimeException(e);
        }
    }

}
