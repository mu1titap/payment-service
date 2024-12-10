package com.multitap.payment.api.application.PointHistoryPay;

import com.multitap.payment.api.dto.out.PaymentResponseDto;
import com.multitap.payment.api.infrastructure.PaymentInfoRepository;
import com.multitap.payment.api.infrastructure.PaymentResponseDtoRepository;
import com.multitap.payment.api.infrastructure.VoltHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
//        ArrayList<PointHistoryResponseDto> arrayList = new ArrayList<>();
//
//        paymentInfoRepository.findByMenteeUuid(menteeUuid).get().forEach(paymentInfo ->
//            arrayList.add(PointHistoryResponseDto.builder()
//                .point(paymentInfo.getVolt())
//                .date(paymentInfo.getCreatedAt())
//                .isPayment(true)
//                .build()));
//
//        voltHistoryRepository.findByMenteeUuid(menteeUuid).get().forEach(voltHistory ->
//            arrayList.add(PointHistoryResponseDto.builder()
//                .point(voltHistory.getVolt())
//                .date(voltHistory.getCreatedAt())
//                .isPayment(false)
//                .build()));
//
//        Collections.reverse(arrayList);
        return paymentInfoRepository.getPaymentInfo(menteeUuid);

//        return arrayList;
    }

}
