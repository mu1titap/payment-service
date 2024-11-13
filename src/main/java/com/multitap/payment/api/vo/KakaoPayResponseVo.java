package com.multitap.payment.api.vo;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoPayResponseVo {
    private String tid;
    private String nextRedirectPcUrl;
    private String partnerOrderId;
}
