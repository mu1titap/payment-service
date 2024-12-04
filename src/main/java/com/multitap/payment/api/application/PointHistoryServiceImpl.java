package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.out.PointHistoryResponseDto;
import com.multitap.payment.api.infrastructure.PaymentInfoRepository;
import com.multitap.payment.api.infrastructure.VoltHistoryRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PaymentInfoRepository paymentInfoRepository;
    private final VoltHistoryRepository voltHistoryRepository;


    @Override
    public List<PointHistoryResponseDto> getPointHistory(String menteeUuid) {
        ArrayList<PointHistoryResponseDto> arrayList = new ArrayList<>();

        paymentInfoRepository.findByMenteeUuid(menteeUuid).get().forEach(paymentInfo ->
            arrayList.add(PointHistoryResponseDto.builder()
                .point(paymentInfo.getVolt())
                .date(paymentInfo.getCreatedAt())
                .isPayment(true)
                .build()));

        voltHistoryRepository.findByMenteeUuid(menteeUuid).get().forEach(voltHistory ->
            arrayList.add(PointHistoryResponseDto.builder()
                .point(voltHistory.getVolt())
                .date(voltHistory.getCreatedAt())
                .isPayment(false)
                .build()));

        Collections.reverse(arrayList);
        return arrayList;
    }

}
