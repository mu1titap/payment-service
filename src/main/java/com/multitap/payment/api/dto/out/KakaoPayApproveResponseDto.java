package com.multitap.payment.api.dto.out;

public class KakaoPayApproveResponseDto {
    private String tid;
    private String cid;
    private String partnerOrderId;
    private String partnerUserId;
    private String itemName;
    private Integer quantity;
    private Amount amount;
    private String paymentMethodType;
}
