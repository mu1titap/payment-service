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
    private String partnerUserId;
    private String itemName;
    @Schema(defaultValue = "1")
    private int quantity;
    private int totalAmount;
    private int taxFreeAmount;
    @Schema(defaultValue = "http://localhost:3003/mypage/volt/payment-confirm")
    private String approvalUrl;
    @Schema(defaultValue = "http://localhost:3003/payment-fail")
    private String failUrl;
    @Schema(defaultValue = "http://localhost:3003/payment-cancle")
    private String cancelUrl;
}
