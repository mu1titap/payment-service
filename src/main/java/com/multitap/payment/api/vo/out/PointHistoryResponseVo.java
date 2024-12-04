package com.multitap.payment.api.vo.out;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PointHistoryResponseVo {

    private LocalDateTime date;
    private Integer point;
    private Boolean isPayment;
}
