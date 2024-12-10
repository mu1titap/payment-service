package com.multitap.payment.api.application.PointHistoryPay;

import com.multitap.payment.api.dto.out.PaymentResponseDto;
import com.multitap.payment.api.infrastructure.PaymentInfoRepository;
import com.multitap.payment.api.infrastructure.PaymentResponseDtoRepository;
import com.multitap.payment.api.infrastructure.VoltHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

        // paging 기능은 native query가 자동으로 처리 가능
        // 정렬은 명시적으로 추가해야 함
        Pageable pageable = PageRequest.of(page, size);

        return paymentInfoRepository.getPaymentInfo(menteeUuid, pageable);

    }

}
