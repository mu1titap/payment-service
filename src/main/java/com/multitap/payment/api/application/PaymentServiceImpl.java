package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.PaymentInfoDto;
import com.multitap.payment.api.infrastructure.PaymentInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentInfoRepository paymentInfoRepository;

    @Override
    public void savePayment(PaymentInfoDto paymentInfoDto) {
        paymentInfoRepository.save(paymentInfoDto.toEntity());
    }
}
