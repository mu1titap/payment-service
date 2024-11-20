package com.multitap.payment.api.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class UserReqDto {

    private String userUuid;
    private Integer pointQuantity;

    public UserReqDto changeSignOfPoint() {
        pointQuantity = -pointQuantity;
        return this;
    }
}
