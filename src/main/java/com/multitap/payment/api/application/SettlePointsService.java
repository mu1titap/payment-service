package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.SettlePointsDto;

public interface SettlePointsService {

    Boolean settlePoints(SettlePointsDto settlePointsDto);

    void sendRandomNumber(String phoneNumber);

    Boolean checkRandomNumber(String insertNumber, String insertedNumber);
}
