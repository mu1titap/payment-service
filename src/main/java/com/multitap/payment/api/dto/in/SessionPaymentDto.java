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
    private String mentorUuid;
    private Integer volt;
    private String mentoringName;
    private String nickName;


    public static SessionPaymentDto from(SessionPaymentVo sessionPaymentVo) {
        return SessionPaymentDto.builder()
            .sessionUuid(sessionPaymentVo.getSessionUuid())
            .menteeUuid(sessionPaymentVo.getMenteeUuid())
            .mentorUuid(sessionPaymentVo.getMentorUuid())
            .volt(sessionPaymentVo.getVolt())
            .mentoringName(sessionPaymentVo.getMentoringName())
            .nickName(sessionPaymentVo.getNickName())
            .build();
    }

    public VoltHistory toEntity() {
        return VoltHistory.builder()
            .sessionUuid(sessionUuid)
            .menteeUuid(menteeUuid)
            .mentorUuid(mentorUuid)
            .volt(volt)
            .build();

    }

}
