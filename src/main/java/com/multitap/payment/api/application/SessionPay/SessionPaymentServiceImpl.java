package com.multitap.payment.api.application.SessionPay;

import com.multitap.payment.api.application.UserServiceClient;
import com.multitap.payment.api.application.kafka.KafkaProducer;
import com.multitap.payment.api.dto.in.SessionPaymentDto;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.api.dto.out.PaySessionRequestDto;
import com.multitap.payment.api.infrastructure.VoltHistoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SessionPaymentServiceImpl implements SessionPaymentService {

    private final UserServiceClient userServiceClient;
    private final VoltHistoryRepository voltHistoryRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public void paySession(SessionPaymentDto sessionPaymentDto) {
        log.info("userUuid in sessionPaymentImpl : {}", sessionPaymentDto.getMenteeUuid());
        // mentee point 사용
        userServiceClient.usePoints(sessionPaymentDto.getMenteeUuid(),
            sessionPaymentDto.getVolt());
        log.info("here in paySession try");

        // mentor point 증가
        UserReqDto userReqDto = UserReqDto.builder()
            .userUuid(sessionPaymentDto.getMentorUuid())
            .pointQuantity(sessionPaymentDto.getVolt())
            .build();
        userServiceClient.addPoints(userReqDto);
        // 결제 정보 저장
        log.info("sessionPaymentDto : {}", sessionPaymentDto.toString());
        voltHistoryRepository.save(sessionPaymentDto.toEntity());

        // kafka topic 발행
        kafkaProducer.sendPaySessionMessage("pay-session", PaySessionRequestDto.builder()
            .sessionUuid(sessionPaymentDto.getSessionUuid())
            .menteeUuid(sessionPaymentDto.getMenteeUuid())
            .mentorUuid(sessionPaymentDto.getMentorUuid())
            .volt(sessionPaymentDto.getVolt())
            .build());


    }


}
