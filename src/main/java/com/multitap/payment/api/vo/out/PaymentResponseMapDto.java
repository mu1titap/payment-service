package com.multitap.payment.api.vo.out;

import com.multitap.payment.api.dto.out.PaymentResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseMapDto {

    private Integer totalPage; // Total count
    private List<PaymentResponseDto> data;
}
