package com.multitap.payment.api.domain;

public enum ExchangeStatus {
    PENDING("대기중"),
    APPROVED("승인됨"),
    REJECTED("거절됨");

    private final String status;

    ExchangeStatus(String status) {
        this.status = status;
    }
}
