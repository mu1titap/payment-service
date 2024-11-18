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
    @Schema(defaultValue = "0", description = "유저 볼트 보유량 update를 위해 필요한 정보")
    private Integer quantity;
}
