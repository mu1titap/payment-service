package com.multitap.payment.api.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SessionPaymentVo {

    private String sessionUuid;
    private String userUuid;
    private Integer volt;

}
