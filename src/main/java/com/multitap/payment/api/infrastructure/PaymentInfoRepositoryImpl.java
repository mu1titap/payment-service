//package com.multitap.payment.api.infrastructure;
//
//import static com.multitap.payment.api.domain.QPaymentInfoEntity.paymentInfoEntity;
//
//import com.multitap.payment.api.dto.out.PaymentResponseDto;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@Slf4j
//@RequiredArgsConstructor
//public class PaymentInfoRepositoryImpl implements PaymentInfoRepositoryCustom {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//
//    @Override
//    public PaymentResponseDto getPaymentInfo(String menteeUuid) {
//
//        List<PaymentResponseDto> paymentResponseDtoList = jpaQueryFactory
//            .select(
//                Projections.fields(PaymentResponseDto.class,
//                    paymentInfoEntity.volt.as("volt"),
//                    paymentInfoEntity.createdAt.as("createdAt")
//                ))
//            .from(paymentInfoEntity)
//
//            .where(paymentInfoEntity.menteeUuid.eq(menteeUuid))
//            .fetch();
//
//        return null;
//    }
//
//}
