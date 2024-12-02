package com.multitap.payment.batch.dto;

import com.multitap.payment.api.domain.BankCode;
import com.multitap.payment.api.domain.Exchange;
import com.multitap.payment.api.domain.ExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeBatchDto {

    private Long id;
    private String mentorUuid;
    private Integer volt;
    private String account;
    private ExchangeStatus status;
    private BankCode bankCode;

    public Exchange toEntity(ExchangeStatus status) {
        return Exchange.builder()
            .id(id)
            .mentorUuid(mentorUuid)
            .volt(volt)
            .account(account)
            .status(status)
            .bankCode(bankCode)
            .build();
    }

}
