package com.multitap.payment.api.dto.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Amount {
    private Integer total;
    private Integer taxFree;

}
