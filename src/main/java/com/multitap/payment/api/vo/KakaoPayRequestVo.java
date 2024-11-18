package com.multitap.payment.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoPayRequestVo {

    @Schema(defaultValue = "TC0ONETIME")
    private String cid;
    private String partnerOrderId;
    private String partnerUserId;
    private String itemName;
    @Schema(defaultValue = "1")
    private int quantity;
    private int totalAmount;
    private int taxFreeAmount;
    @Schema(defaultValue = "https://lims-dev.tistory.com")
    private String approvalUrl;
    @Schema(defaultValue = "https://pknustu.youngjin.com")
    private String failUrl;
    @Schema(defaultValue = "https://github.com")
    private String cancelUrl;
}
