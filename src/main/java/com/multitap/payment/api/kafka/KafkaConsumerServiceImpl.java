package com.multitap.payment.api.kafka;

import com.multitap.payment.api.application.UserServiceClient;
import com.multitap.payment.api.dto.in.UserReqDto;
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
    private final UserServiceClient userServiceClient;

    @Override
    @Transactional
    public void updateVoltHistoryStatus(
        SessionConfirmedDto sessionConfirmedDto) { // List로 안 오고 개별적으로 발행됨?
        // 멘토만? 멘티는? 
        // 멘토 멘티 둘 다 입력 받아서 저장하고 있나? -> Yes
        voltHistoryRepository.findBySessionUuid(sessionConfirmedDto.getSessionUuid())
            .ifPresentOrElse(
                voltHistoryList -> {
                    voltHistoryList.forEach(v -> {
                        // 멘토 포인트 증가
                        userServiceClient.addPoints(UserReqDto.builder()
                            .userUuid(
                                sessionConfirmedDto.getMentorUuid())    // mentorUuid 는 null인 상태
                            .pointQuantity(v.getVolt()) // todo field 바뀌면 dto에서 값 얻기 
                            .build());

                        v.updatePaymentStatus(sessionConfirmedDto); // 결제 완료 처리
                        voltHistoryRepository.save(v);
                        // 멘티는 먼저 감소
                        // 멘토 포인트 증가 -> feginClient로 멘토 포인트 증가

                    });

                },
                () -> log.error("VoltHistory not found for sessionUuid: {}",
                    sessionConfirmedDto.getSessionUuid())
            );

        log.info("sessionConfirmedDto: {}", sessionConfirmedDto.toString());


    }

}
