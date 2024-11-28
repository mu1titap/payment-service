package com.multitap.payment.batch.repository;

import com.multitap.payment.batch.entity.ExchangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeLogRepository extends JpaRepository<ExchangeLog, Long> {
    
}
