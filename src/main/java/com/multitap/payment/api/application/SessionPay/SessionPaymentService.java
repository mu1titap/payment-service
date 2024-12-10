package com.multitap.payment.api.application.SessionPay;

import com.multitap.payment.api.dto.in.SessionPaymentDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SessionPaymentService {

    void paySession(SessionPaymentDto sessionPaymentDto);


}



