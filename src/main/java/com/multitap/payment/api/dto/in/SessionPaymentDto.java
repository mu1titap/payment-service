package com.multitap.payment.api.dto.in;


import com.multitap.payment.api.domain.VoltHistory;
import com.multitap.payment.api.vo.SessionPaymentVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class SessionPaymentDto {

    private String sessionUuid;
    private String menteeUuid;
    private Integer volt;


    public static SessionPaymentDto from(SessionPaymentVo sessionPaymentVo) {
        return SessionPaymentDto.builder()
            .sessionUuid(sessionPaymentVo.getSessionUuid())
            .menteeUuid(sessionPaymentVo.getMenteeUuid())
            .volt(sessionPaymentVo.getVolt())
            .build();
    }

    public VoltHistory toEntity() {
        return VoltHistory.builder()
            .sessionUuid(sessionUuid)
            .menteeUuid(menteeUuid)
            .volt(volt)
            .build();

    }

}
