package com.multitap.payment.api.application.SessionPay;

import com.multitap.payment.api.dto.in.SessionPaymentDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SessionPaymentService {

    // 1. mentee point 사용
    // 2. voltHistory entity에 결제 정보 저장
    void paySession(SessionPaymentDto sessionPaymentDto);


}



