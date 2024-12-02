package com.multitap.payment.api.application;

import com.multitap.payment.api.domain.VoltHistory;
import com.multitap.payment.api.dto.in.SettlePointsDto;
import com.multitap.payment.api.dto.out.VoltHistoryDto;
import com.multitap.payment.api.infrastructure.VoltHistoryRepository;
import com.multitap.payment.api.vo.VoltResponse;
import com.multitap.payment.common.entity.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SettlePointsServiceImpl implements SettlePointsService {

    private final UserServiceClient userServiceClient;
    private final VoltHistoryRepository voltHistoryRepository;

    @Override
    public Boolean settlePoints(SettlePointsDto settlePointsDto) {
        BaseResponse<Boolean> response =
            userServiceClient.usePoints(settlePointsDto.getMentorUuid(),
                settlePointsDto.getPoints());

        return response.result();
    }

    @Override
    public VoltHistoryDto getVoltHistory(String mentorUuid) {
        List<VoltHistory> voltHistoryList = voltHistoryRepository.findByMentorUuid(mentorUuid);
        if (voltHistoryList.isEmpty()) {
            return null;
        }

        Integer voltAmount = 0;
        for (VoltHistory voltHistory : voltHistoryList) {
            voltAmount += voltHistory.getVolt();
        }

        VoltHistoryDto voltHistoryDto = new VoltHistoryDto();

        List<VoltResponse> voltResponseList =
            voltHistoryList.stream().map(voltHistory ->
                    VoltResponse.builder()
                        .volt(voltHistory.getVolt())
                        .date(voltHistory.getCreatedAt())
                        .sender(voltHistory.getMenteeUuid())
                        .build())
                .toList();

        voltHistoryDto.setVoltResponse(voltAmount, voltResponseList);

        return voltHistoryDto;

    }

}
