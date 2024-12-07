package com.multitap.payment.api.infrastructure;

import com.multitap.payment.api.domain.VoltHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoltHistoryRepository extends JpaRepository<VoltHistory, Long> {

    List<VoltHistory> findByMentorUuid(String uuid);

    Optional<List<VoltHistory>> findByMenteeUuid(String uuid);

}
