package com.multitap.payment.api.domain;

public enum ExchangeStatus {
    PROCEEDING("진행중"),
    COMPLETED("진행완료"),
    FAILED("실패");


    private final String status;

    ExchangeStatus(String status) {
        this.status = status;
    }
}
