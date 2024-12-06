package com.multitap.payment.api.application.SettlePay;


import com.multitap.payment.api.dto.in.ExchangePointsDto;
import com.multitap.payment.api.dto.out.ExchangeDto;
import com.multitap.payment.api.dto.out.VoltHistoryDto;

public interface SettlePointsService {

    Boolean settlePoints(ExchangePointsDto settlePointsDto);

    VoltHistoryDto getVoltHistory(String mentorUuid);

    ExchangeDto getExchange(String startDate, String endDate, String mentorUuid);
}
