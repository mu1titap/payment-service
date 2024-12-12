package com.multitap.payment.api.common.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankCode {
    KOREA_SC("001"),
    KOREA_SHINHAN("002"),
    KOREA_KB("003"),
    KOREA_HANA("004"),
    KOREA_IBK("005"),
    KOREA_NH_BANK("006");

    private final String code;
}
