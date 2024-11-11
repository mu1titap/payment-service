package com.multitap.payment.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class KakaoPayApproveRequestVo {

    @Schema(defaultValue = "TC0ONETIME")
    private String cid;

    private String tid;

    private String partnerOrderId;

    private String partnerUserId;

    private String pgToken;
}
