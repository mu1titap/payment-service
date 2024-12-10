package com.multitap.payment.api.dto.in;

import com.multitap.payment.api.common.enums.BankCode;
import com.multitap.payment.api.domain.Exchange;
import com.multitap.payment.api.vo.ExchangePointsVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangePointsDto {

    private String mentorUuid;
    private Integer points;
    private String account;
    private BankCode bankCode;


    public static ExchangePointsDto of(ExchangePointsVo exchangePointsVo) {
        return ExchangePointsDto.builder()
            .mentorUuid(exchangePointsVo.getMentorUuid())
            .points(exchangePointsVo.getPoints())
            .account(exchangePointsVo.getAccount())
            .bankCode(exchangePointsVo.getBankCode())
            .build();
    }

    public Exchange toEntity() {
        return Exchange.builder()
            .mentorUuid(mentorUuid)
            .volt(points)
            .account(account)
            .bankCode(bankCode)
            .build();
    }

}
