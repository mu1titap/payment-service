package com.multitap.payment.api.dto.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaySessionRequestDto {

    private String sessionUuid;
    private String menteeUuid;
    private String mentorUuid;
    private Integer volt;
    private String mentoringName;
    private String nickname;
}
