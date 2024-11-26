package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.SettlePointsDto;
import com.multitap.payment.common.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SettlePointsServiceImpl implements SettlePointsService {

    private final UserServiceClient userServiceClient;

    @Override
    public Boolean settlePoints(SettlePointsDto settlePointsDto) {
        BaseResponse<Boolean> response =
            userServiceClient.usePoints(settlePointsDto.getMentorUuid(),
                settlePointsDto.getPoints());

        return response.result();
    }

}
