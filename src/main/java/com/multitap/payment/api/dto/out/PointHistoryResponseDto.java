package com.multitap.payment.api.dto.out;


import com.multitap.payment.api.vo.out.PointHistoryResponseVo;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PointHistoryResponseDto implements Comparable<PointHistoryResponseDto> {

    private Integer point;
    private LocalDateTime date;
    private Boolean isPayment;

    public static PointHistoryResponseDto of(PointHistoryResponseVo vo) {
        return PointHistoryResponseDto.builder()
            .date(vo.getDate())
            .point(vo.getPoint())
            .isPayment(vo.getIsPayment())
            .build();
    }

    @Override
    public int compareTo(PointHistoryResponseDto o) {
        return this.date.compareTo(o.date);
    }

}
