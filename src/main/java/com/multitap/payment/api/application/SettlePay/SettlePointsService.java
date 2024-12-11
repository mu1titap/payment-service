package com.multitap.payment.api.application.SettlePay;


import com.multitap.payment.api.dto.in.ExchangePointsDto;
import com.multitap.payment.api.dto.out.ExchangeDto;
import com.multitap.payment.api.dto.out.VoltHistoryDto;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface SettlePointsService {

    @Transactional
    Boolean settlePoints(ExchangePointsDto settlePointsDto);


    VoltHistoryDto getVoltHistory(String mentorUuid);

    Boolean checkRandomNumber(String userUuid, String insertedNumber);

    void deleteRandomNumber(String userUuid);

    void deleteVerifiedUser(String userUuid);

    ExchangeDto getExchange(String startDate, String endDate, String mentorUuid);

    void sendRandomNumber(String userUuid);


}
