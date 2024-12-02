package com.multitap.payment.api.vo;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VoltResponse {

    private Integer volt;
    private LocalDateTime date;
    private String sender;
}
