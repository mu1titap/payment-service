package com.multitap.payment.api.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SessionPaymentVo {

    private String sessionId;
    private String userUuid;
    private Integer pointPrice;

}
