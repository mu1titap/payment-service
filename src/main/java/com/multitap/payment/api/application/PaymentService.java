package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.PaymentInfoDto;

public interface PaymentService {
    void savePayment(PaymentInfoDto paymentInfoDto);
}
