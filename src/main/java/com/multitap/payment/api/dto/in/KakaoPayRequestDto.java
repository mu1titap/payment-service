package com.multitap.payment.api.dto.in;

import com.multitap.payment.api.domain.KakaoPay;
import com.multitap.payment.api.vo.in.KakaoPayRequestVo;
import com.multitap.payment.common.utils.PartnerOrderCodeGenerator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayRequestDto {

    // ercloud 보고 작성한 거. => 맞게 수정 필요
    @Schema(defaultValue = "TC0ONETIME")
    private String cid;

    private String partnerOrderId;

    private String partnerUserId;

    private String itemName;

    private Integer quantity;

    private Integer totalAmount;

    private Integer taxFreeAmount;

    private String approvalUrl;

    private String failUrl;

    private String cancelUrl;

    public KakaoPay toEntity() {
        return KakaoPay.builder()
            .partnerOrderId(partnerOrderId)
            .partnerUserId(partnerUserId)
            .partnerOrderId(partnerOrderId)
            .cid(cid)
            .quantity(quantity)
            .itemName(itemName)
            .totalAmount(totalAmount)
            .taxFreeAmount(taxFreeAmount)
            .build();
    }

    public static KakaoPayRequestDto from(KakaoPayRequestVo vo) {
        return KakaoPayRequestDto.builder()
            .partnerOrderId(PartnerOrderCodeGenerator.generateCategoryCode("PO-"))
            .partnerUserId(vo.getPartnerUserId())
            .cid(vo.getCid())
            .quantity(vo.getQuantity())
            .itemName(vo.getItemName())
            .totalAmount(vo.getTotalAmount())
            .taxFreeAmount(vo.getTaxFreeAmount())
            .approvalUrl(vo.getApprovalUrl())
            .failUrl(vo.getFailUrl())
            .cancelUrl(vo.getCancelUrl())
            .build();
    }


}
