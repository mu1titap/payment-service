package com.multitap.payment.api.application.PointHistoryPay;

import com.multitap.payment.api.dto.out.PaymentResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PointHistoryService {

    List<PaymentResponseDto> getPointHistory(String menteeUuid, Integer size, Integer page);


}
