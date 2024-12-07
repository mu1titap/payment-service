package com.multitap.payment.api.dto.out;

import com.multitap.payment.api.common.enums.ExchangeStatus;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDtoList {

    private Long id;
    private LocalDate date;
    private Integer volt;
    private ExchangeStatus status;
    private Integer money;
}
