package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.ExchangePointsDto;
import com.multitap.payment.api.infrastructure.ExchangeRepository;
import com.multitap.payment.common.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SettlePointsServiceImpl implements SettlePointsService {

    private final UserServiceClient userServiceClient;
    private final ExchangeRepository exchangeRepository;


    @Override
    public Boolean settlePoints(ExchangePointsDto exchangePointsDto) {
        // todo 인증 성공 시 되도록 인증 요청 api 따로 만들기
        // todo jwt/redis 에 정보 담기
        BaseResponse<Boolean> response =
            userServiceClient.usePoints(exchangePointsDto.getMentorUuid(),
                exchangePointsDto.getPoints());

        if (!response.result()) {
            log.error("Failed to settle points. mentorUuid: {}, points: {}",
                exchangePointsDto.getMentorUuid(), exchangePointsDto.getPoints());
            // Rollback
            // todo rollback client 다시 만들기
            // usePoints 의 내부 로직 모르는 상태에서 너무 막연한 방법
            userServiceClient.usePoints(exchangePointsDto.getMentorUuid(),
                exchangePointsDto.getPoints() * (-1));
            return false;
        }

        exchangeRepository.save(exchangePointsDto.toEntity());

        return true;
    }

}
