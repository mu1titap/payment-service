package com.multitap.payment.api.dto.in;

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
}
