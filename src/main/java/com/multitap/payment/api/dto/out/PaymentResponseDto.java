package com.multitap.payment.api.dto.out;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {

    private Integer point;
    private LocalDateTime date;
    private String source_table;
}
