package com.multitap.payment.api.infrastructure;

import com.multitap.payment.api.domain.VoltHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoltHistoryRepository extends JpaRepository<VoltHistory, Long> {

    List<VoltHistory> findByMentorUuid(String uuid);

    Optional<List<VoltHistory>> findByMenteeUuid(String uuid);

    @Query("SELECT v FROM VoltHistory v WHERE v.sessionUuid = :sessionUuid AND v.paymentStatus = 'PENDING'")
    Optional<List<VoltHistory>> findBySessionUuid(String sessionUuid);

}
