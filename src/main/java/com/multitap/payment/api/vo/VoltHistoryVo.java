package com.multitap.payment.api.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoltHistoryVo {

    private Integer totalVolt;
    private List<VoltResponse> voltList;

}

