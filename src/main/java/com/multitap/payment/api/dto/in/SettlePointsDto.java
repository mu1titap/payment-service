package com.multitap.payment.api.dto.in;

import com.multitap.payment.api.vo.SettlePointsVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SettlePointsDto {

    private String mentorUuid;
    private Integer points;


    public static SettlePointsDto of(SettlePointsVo settlePointsVo) {
        return SettlePointsDto.builder()
            .mentorUuid(settlePointsVo.getMentorUuid())
            .points(settlePointsVo.getPoints())
            .build();
    }

}
