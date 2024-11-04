package com.multitap.payment.api.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class KakaoPayRequestVo {
    private String cid;
    private String partnerUserId;
    private String itemName;
    private int quantity;
    private int totalAmount;
    private int taxFreeAmount;
    private String approvalUrl;
    private String failUrl;
    private String cancelUrl;
}
