package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.SessionPaymentDto;
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

    @Override
    public void paySession(SessionPaymentDto sessionPaymentDto) {
        // todo error handling
        log.info("userUuid in sessionPaymentImpl : {}", sessionPaymentDto.getUserUuid());
        userServiceClient.usePoints(sessionPaymentDto.getUserUuid(),
            sessionPaymentDto.getVolt());
        log.info("here in paySession try");

        // 결제 정보 저장
        log.info("sessionPaymentDto : {}", sessionPaymentDto.toString());
        voltHistoryRepository.save(sessionPaymentDto.toEntity());


    }


}
