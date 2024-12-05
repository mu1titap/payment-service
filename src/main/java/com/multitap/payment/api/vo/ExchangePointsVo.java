package com.multitap.payment.api.vo;

import com.multitap.payment.api.domain.enum_file.BankCode;
import lombok.Getter;

@Getter
public class ExchangePointsVo {

    private String mentorUuid;
    private Integer points;
    private String account;
    private BankCode bankCode;

}
