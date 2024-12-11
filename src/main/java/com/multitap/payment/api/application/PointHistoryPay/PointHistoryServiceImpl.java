package com.multitap.payment.api.application.PointHistoryPay;

import com.multitap.payment.api.infrastructure.PaymentInfoRepository;
import com.multitap.payment.api.vo.out.PaymentResponseMapDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PaymentInfoRepository paymentInfoRepository;


    @Override
    public PaymentResponseMapDto getPointHistory(String menteeUuid, Integer size,
        Integer page, String criteria) {    // 매개변수 재할당은 지양해야 한다

        // paging 기능은 native query가 자동으로 처리 가능
        // 정렬은 JPA가 자동으로 처리 불가
        // 정렬은 명시적으로 추가 x -> native Query에서 동적으로 처리하면 " " 감싸져서 "DESC" 되어 버림
        // Sort 객체 생성
        Sort.Direction direction = Sort.Direction.fromString(criteria.toUpperCase());
        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        String sortDirection = criteria == null ? "DESC" :  // criteria가 null이면 DESC로 처리
            criteria.equalsIgnoreCase("ASC") ? "ASC" : "DESC";

        Integer counts = paymentInfoRepository.getPaymentInfoCount(menteeUuid);
        Integer toalPage = (int) Math.ceil((double) counts / size);    // 페이지 수 올림
        log.info("counts {}", counts);

        return new PaymentResponseMapDto(toalPage,
            paymentInfoRepository.getPaymentInfo(menteeUuid, sortDirection, pageable));

    }

}
