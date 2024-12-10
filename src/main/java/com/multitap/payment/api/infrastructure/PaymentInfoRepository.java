package com.multitap.payment.api.infrastructure;

import com.multitap.payment.api.domain.PaymentInfoEntity;
import com.multitap.payment.api.dto.out.PaymentResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, Long> {


    @Query(name = "payment_info_dto", nativeQuery = true)
    List<PaymentResponseDto> getPaymentInfo(@Param("menteeUuid") String menteeUuid,
        @Param("criteria") String criteria,
        Pageable pageable);
}
