package com.multitap.payment.api.application.PointHistoryPay;

import com.multitap.payment.api.dto.out.PointHistoryResponseDto;
import java.util.List;

public interface PointHistoryService {

    List<PointHistoryResponseDto> getPointHistory(String menteeUuid);

}
