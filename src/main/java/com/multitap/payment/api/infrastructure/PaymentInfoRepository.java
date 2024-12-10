package com.multitap.payment.api.infrastructure;

import com.multitap.payment.api.domain.PaymentInfoEntity;
import com.multitap.payment.api.dto.out.PaymentResponseDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, Long> {

    Optional<List<PaymentInfoEntity>> findByMenteeUuid(String menteeUuid);

    @Query(name = "payment_info_dto", nativeQuery = true)
    List<PaymentResponseDto> getPaymentInfo(@Param("menteeUuid") String menteeUuid);
}
