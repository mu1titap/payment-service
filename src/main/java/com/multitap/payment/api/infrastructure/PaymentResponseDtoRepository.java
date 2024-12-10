package com.multitap.payment.api.infrastructure;

import com.multitap.payment.api.domain.PaymentInfoEntity;
import com.multitap.payment.api.dto.out.PaymentResponseDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PaymentResponseDtoRepository extends JpaRepository<PaymentInfoEntity, Long> {

    @Query(value = "SELECT p.volt, p.created_at AS createdAt, 'payment_info' AS sourceTable "
        + "FROM payment_info p "
        + "WHERE p.mentee_uuid = :menteeUuid "
        + "UNION ALL "
        + "SELECT v.volt, v.created_at AS createdAt, 'volt_history' AS sourceTable "
        + "FROM volt_history v "
        + "WHERE v.mentee_uuid = :menteeUuid",
        nativeQuery = true)
    List<PaymentResponseDto> getPaymentInfo(@Param("menteeUuid") String menteeUuid);
}