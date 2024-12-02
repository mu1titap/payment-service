package com.multitap.payment.api.infrastructure;

import com.multitap.payment.api.domain.Exchange;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

//    @Query("SELECT e FROM Exchange e WHERE e.status = ?1 "
//        + " ORDER BY e.createdAt LIMIT ?2 ")
//    List<Exchange> findByStatus(ExchangeStatus status, Integer limit);

    @Query("SELECT e FROM Exchange e  "
        + " ORDER BY e.createdAt")
    Page<Exchange> findManyThing(Pageable pageable);

    List<Exchange> findByMentorUuidAndCreatedAtBetween(String mentorUuid, LocalDateTime startDate,
        LocalDateTime endDate);
}
