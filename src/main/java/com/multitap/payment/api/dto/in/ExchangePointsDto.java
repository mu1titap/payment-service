package com.multitap.payment.api.dto.in;

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


    public static ExchangePointsDto of(ExchangePointsVo exchangePointsVo) {
        return ExchangePointsDto.builder()
            .mentorUuid(exchangePointsVo.getMentorUuid())
            .points(exchangePointsVo.getPoints())
            .build();
    }

}
