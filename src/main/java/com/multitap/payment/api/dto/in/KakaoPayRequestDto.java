package com.multitap.payment.api.dto.in;

import com.multitap.payment.api.vo.KakaoPayRequestVo;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayRequestDto {
    private Long id;

    private String partnerOrderId;

    private String partnerUserId;

    private String cid;

    private Integer quantity;

    private String itemName;

    private Integer totalAmount;

    private Integer taxFreeAmount;

    private static KakaoPayRequestVo toVo(KakaoPayRequestDto requestDto) {
        return KakaoPayRequestVo.builder()
               .cid(requestDto.getCid())
               .partnerUserId(requestDto.getPartnerUserId())
               .itemName(requestDto.getItemName())
               .quantity(requestDto.getQuantity())
               .totalAmount(requestDto.getTotalAmount())
               .taxFreeAmount(requestDto.getTaxFreeAmount())
               .build();
    }



}
