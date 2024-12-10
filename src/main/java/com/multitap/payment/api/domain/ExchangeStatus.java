package com.multitap.payment.api.domain;

public enum ExchangeStatus {
    PROCEEDING("진행중"),
    COMPLETED("진행완료");


    private final String status;

    ExchangeStatus(String status) {
        this.status = status;
    }
}