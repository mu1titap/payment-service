package com.multitap.payment.api.application.PointHistoryPay;

import com.multitap.payment.api.dto.out.PointHistoryResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PointHistoryService {

    List<PointHistoryResponseDto> getPointHistory(String menteeUuid);

}
