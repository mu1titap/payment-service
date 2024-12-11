package com.multitap.payment.api.infrastructure;

import com.multitap.payment.api.domain.PaymentInfoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, Long> {

    Optional<List<PaymentInfoEntity>> findByMenteeUuid(String menteeUuid);
}
