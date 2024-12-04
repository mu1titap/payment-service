package com.multitap.payment.api.dto.out;

import com.multitap.payment.api.vo.out.VoltResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoltHistoryDto {

    private Integer totalVolt;
    private List<VoltResponse> voltList;

    public static VoltHistoryDto of(Integer totalVolt, List<VoltResponse> voltList) {
        return VoltHistoryDto.builder()
            .totalVolt(totalVolt)
            .voltList(voltList)
            .build();
    }

    public void setVoltResponse(Integer totalVolt, List<VoltResponse> voltList) {
        this.totalVolt = totalVolt;
        this.voltList = voltList;
    }

}
