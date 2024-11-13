package com.multitap.payment.api.dto.in;

import com.multitap.payment.api.domain.PaymentInfoEntity;
import com.multitap.payment.api.domain.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@ToString
@Setter
public class PaymentInfoDto {
    private String menteeUuid;
    private Integer volt;
    private PaymentType type;
    private Integer cash;

    public PaymentInfoEntity toEntity(){
        return PaymentInfoEntity.builder()
                .menteeUuid(menteeUuid)
                .volt(volt)
                .type(type)
                .cash(cash)
                .build();
    }

}
