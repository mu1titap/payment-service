package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.SettlePointsDto;

public interface SettlePointsService {

    Boolean settlePoints(SettlePointsDto settlePointsDto);

    void sendRandomNumber(String userUuid);

    Boolean checkRandomNumber(String userUuid, String insertedNumber);

    void deleteRandomNumber(String userUuid);

    void deleteVerifiedUser(String userUuid);

}
