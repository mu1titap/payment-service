package com.multitap.payment.api.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoPayRequestVo {

    @Schema(defaultValue = "TC0ONETIME")
    private String cid;
    private String partnerOrderId;
    @Schema(defaultValue = "459d827a-59b2-43b7-a015-38bde218a3bc")
    private String partnerUserId;
    @Schema(defaultValue = "VOLT")
    private String itemName;
    @Schema(defaultValue = "1")
    private int quantity;
    @Schema(defaultValue = "151")
    private int totalAmount;
    @Schema(defaultValue = "0")
    private int taxFreeAmount;
    @Schema(defaultValue = "http://localhost:3003/mypage/volt/payment-confirm")
    private String approvalUrl;
    @Schema(defaultValue = "http://localhost:3003/payment-fail")
    private String failUrl;
    @Schema(defaultValue = "http://localhost:3003/payment-cancle")
    private String cancelUrl;
}
