package com.multitap.payment.api.application.SettlePay;

import com.multitap.payment.api.dto.in.ExchangePointsDto;

public interface SettlePointsService {

    Boolean settlePoints(ExchangePointsDto exchangePointsDto);

    void sendRandomNumber(String userUuid);

    Boolean checkRandomNumber(String userUuid, String insertedNumber);

    void deleteRandomNumber(String userUuid);

    void deleteVerifiedUser(String userUuid);

}
