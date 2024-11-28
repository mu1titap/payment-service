package com.multitap.payment.batch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ExchangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long exchangeId;
    @Enumerated(EnumType.STRING)
    private ExchangeLogStatus status;

    @Builder
    public ExchangeLog(Long exchangeId, ExchangeLogStatus status) {
        this.exchangeId = exchangeId;
        this.status = status;
    }

}
