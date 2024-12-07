package com.multitap.payment.api.dto.out;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDto {

    private Integer totalExchange;
    private List<ExchangeDtoList> exchangeList;
}
