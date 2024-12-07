package com.multitap.payment.api.kafka.messageIn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SessionConfirmedDto {

    private String sessionUuid;
    private Boolean sessionIsConfirmed;
    private String mentorUuid;
}
