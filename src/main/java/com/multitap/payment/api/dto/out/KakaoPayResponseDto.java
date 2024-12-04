package com.multitap.payment.api.dto.out;

import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.vo.out.KakaoPayResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayResponseDto {

    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // web - 받는 결제 페이지
    private String partnerOrderId;

    public KakaoPayResponseVo toVo() {
        return KakaoPayResponseVo.builder()
            .tid(tid)
            .nextRedirectPcUrl(next_redirect_pc_url)
            .partnerOrderId(partnerOrderId)
            .build();
    }

    public void setPartnerOrderIdToResponse(String partnerOrderId) {
        this.partnerOrderId = partnerOrderId;
    }

    public static KakaoPayResponseDto toDto(KakaoPayResponseDto v1, KakaoPayRequestDto v2) {
        return KakaoPayResponseDto.builder()
            .tid(v1.getTid())
            .next_redirect_pc_url(v1.getNext_redirect_pc_url())
            .partnerOrderId(v2.getPartnerOrderId())
            .build();
    }

}
