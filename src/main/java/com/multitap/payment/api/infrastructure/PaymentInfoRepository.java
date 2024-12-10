package com.multitap.payment.api.infrastructure;

import com.multitap.payment.api.domain.PaymentInfoEntity;
import com.multitap.payment.api.dto.out.PaymentResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, Long> {


    @Query(name = "payment_info_dto", nativeQuery = true)
    List<PaymentResponseDto> getPaymentInfo(@Param("menteeUuid") String menteeUuid,
        Pageable pageable, @Param("criteria") String criteria);
}
