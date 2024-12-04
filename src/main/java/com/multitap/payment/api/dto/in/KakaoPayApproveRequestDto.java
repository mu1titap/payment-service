package com.multitap.payment.api.dto.in;

import com.multitap.payment.api.vo.in.KakaoPayApproveRequestVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoPayApproveRequestDto {

    private String cid;

    private String tid;

    private String partnerOrderId;

    private String partnerUserId;

    private String pgToken;

    public static KakaoPayApproveRequestDto from(
        KakaoPayApproveRequestVo kakaoPayApproveRequestVo) {
        return KakaoPayApproveRequestDto.builder()
            .cid(kakaoPayApproveRequestVo.getCid())
            .tid(kakaoPayApproveRequestVo.getTid())
            .partnerOrderId(kakaoPayApproveRequestVo.getPartnerOrderId())
            .partnerUserId(kakaoPayApproveRequestVo.getPartnerUserId())
            .pgToken(kakaoPayApproveRequestVo.getPgToken())
            .build();
    }


}
