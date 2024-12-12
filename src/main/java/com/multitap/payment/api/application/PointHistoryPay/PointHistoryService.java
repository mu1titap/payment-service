package com.multitap.payment.api.application.PointHistoryPay;

import com.multitap.payment.api.vo.out.PaymentResponseMapDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PointHistoryService {

    PaymentResponseMapDto getPointHistory(String menteeUuid, Integer size, Integer page,
        String criteria);


}
