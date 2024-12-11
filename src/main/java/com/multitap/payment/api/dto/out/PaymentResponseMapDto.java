package com.multitap.payment.api.dto.out;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponseMapDto {

    private Integer totalPage;
    private List<PaymentResponseDto> paymentResponseDtoList;
}
