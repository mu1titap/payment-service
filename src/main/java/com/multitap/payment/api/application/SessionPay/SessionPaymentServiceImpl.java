package com.multitap.payment.api.application.SessionPay;

import com.multitap.payment.api.application.UserServiceClient;
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
        log.info("userUuid in sessionPaymentImpl : {}", sessionPaymentDto.getMenteeUuid());
        // mentee point 사용
        userServiceClient.usePoints(sessionPaymentDto.getMenteeUuid(),
            sessionPaymentDto.getVolt());
        log.info("here in paySession try");

        // mentor point 증가 => 세션 확정 시 증가하도록
//        UserReqDto userReqDto = UserReqDto.builder()
//            .userUuid(sessionPaymentDto.getMentorUuid())
//            .pointQuantity(sessionPaymentDto.getVolt())
//            .build();
//        userServiceClient.addPoints(userReqDto);
        // 결제 정보 저장
        log.info("sessionPaymentDto : {}", sessionPaymentDto.toString());
        voltHistoryRepository.save(sessionPaymentDto.toEntity());


    }


}
