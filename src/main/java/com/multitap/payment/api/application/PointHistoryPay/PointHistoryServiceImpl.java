package com.multitap.payment.api.application.PointHistoryPay;

import com.multitap.payment.api.dto.out.PaymentResponseDto;
import com.multitap.payment.api.infrastructure.PaymentInfoRepository;
import com.multitap.payment.api.infrastructure.PaymentResponseDtoRepository;
import com.multitap.payment.api.infrastructure.VoltHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PaymentInfoRepository paymentInfoRepository;
    private final VoltHistoryRepository voltHistoryRepository;
    private final PaymentResponseDtoRepository paymentResponseDtoRepository;


    @Override
    public List<PaymentResponseDto> getPointHistory(String menteeUuid, Integer size,
        Integer page) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return paymentInfoRepository.getPaymentInfo(menteeUuid, pageable);

    }

}
