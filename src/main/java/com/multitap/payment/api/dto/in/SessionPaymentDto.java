package com.multitap.payment.api.dto.in;


import com.multitap.payment.api.vo.SessionPaymentVo;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SessionPaymentDto {

    private String sessionId;
    private String userUuid;
    private Integer pointPrice;


    public static SessionPaymentDto from(SessionPaymentVo sessionPaymentVo) {
        return SessionPaymentDto.builder()
            .sessionId(sessionPaymentVo.getSessionId())
            .userUuid(sessionPaymentVo.getUserUuid())
            .pointPrice(sessionPaymentVo.getPointPrice())
            .build();
    }

}
