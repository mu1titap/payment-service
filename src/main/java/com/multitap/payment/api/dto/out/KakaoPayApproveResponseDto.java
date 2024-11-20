package com.multitap.payment.api.dto.out;

import com.multitap.payment.api.domain.KakaoPay;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayApproveResponseDto {

    private String tid;
    private String cid;
    private String partner_order_id;
    private String partner_user_id;
    private String item_name;
    private Integer quantity;
    private Amount amount;
    private String payment_method_type;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    public KakaoPay toEntity() {
        return KakaoPay.builder()
            .cid(cid)
            .partnerOrderId(partner_order_id)
            .partnerUserId(partner_user_id)
            .itemName(item_name)
            .quantity(quantity)
            .totalAmount(amount.getTotal())
            .taxFreeAmount(amount.getTax_free())
            .createdAt(created_at)
            .updatedAt(updated_at)
            .build();
    }
}
