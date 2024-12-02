package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.SettlePointsDto;
import com.multitap.payment.api.dto.out.VoltHistoryDto;

public interface SettlePointsService {

    Boolean settlePoints(SettlePointsDto settlePointsDto);

    VoltHistoryDto getVoltHistory(String mentorUuid);


}
