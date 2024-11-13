package com.multitap.payment.api.dto.in;

import com.multitap.payment.api.domain.MemberVoltAmountEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberVoltAmountDto {
    private String uuid;
    private Integer amount;

    public static MemberVoltAmountDto of(String uuid, Integer amount){
        return MemberVoltAmountDto.builder()
            .uuid(uuid)
            .amount(amount)
            .build();
    }

    public MemberVoltAmountEntity toEntity(){
        return MemberVoltAmountEntity.builder()
           .uuid(uuid)
           .amount(amount)
           .build();
    }


}
