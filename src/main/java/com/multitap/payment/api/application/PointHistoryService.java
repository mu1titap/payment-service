package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.out.PointHistoryResponseDto;
import java.util.List;

public interface PointHistoryService {

    List<PointHistoryResponseDto> getPointHistory(String menteeUuid);

}
