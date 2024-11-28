package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.ExchangePointsDto;

public interface SettlePointsService {

    Boolean settlePoints(ExchangePointsDto settlePointsDto);
}
