package com.multitap.payment.api.vo;

import com.multitap.payment.api.common.enums.PaymentType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentInfoVo {
    private String menteeUuid;
    private Integer volt;
    private PaymentType type;
    private Integer cash;

}
