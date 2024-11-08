package com.multitap.payment.api.dto.in;

import com.multitap.payment.api.common.utils.PartnerOrderCodeGenerator;
import com.multitap.payment.api.domain.KakaoPay;
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

    private String partnerOrderId;

    private String partnerUserId;

    private String cid;

    private Integer quantity;

    private String itemName;

    private Integer totalAmount;

    private Integer taxFreeAmount;

    public KakaoPay toEntity() {
        return KakaoPay.builder()
            .partnerOrderId(partnerOrderId)
            .partnerUserId(partnerUserId)
            .cid(cid)
            .quantity(quantity)
            .itemName(itemName)
            .totalAmount(totalAmount)
            .taxFreeAmount(taxFreeAmount)
            .build();
    }

    public static KakaoPayRequestDto fromVo(KakaoPayRequestVo vo) {
        return KakaoPayRequestDto.builder()
           .partnerOrderId(PartnerOrderCodeGenerator.generateCategoryCode("PO-"))
           .partnerUserId(vo.getPartnerUserId())
           .cid(vo.getCid())
           .quantity(vo.getQuantity())
           .itemName(vo.getItemName())
           .totalAmount(vo.getTotalAmount())
           .taxFreeAmount(vo.getTaxFreeAmount())
           .build();
    }



}
