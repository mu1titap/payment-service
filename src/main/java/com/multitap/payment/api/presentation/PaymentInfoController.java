package com.multitap.payment.api.presentation;

import com.multitap.payment.api.application.PointHistoryPay.PointHistoryService;
import com.multitap.payment.api.application.SettlePay.SettlePointsService;
import com.multitap.payment.api.dto.out.ExchangeDto;
import com.multitap.payment.api.dto.out.PaymentResponseDto;
import com.multitap.payment.api.dto.out.VoltHistoryDto;
import com.multitap.payment.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaymentInfoController {


    private final SettlePointsService settlePointsService;
    private final PointHistoryService pointHistoryService;

    @Operation(summary = "멘토가 받은 포인트 값 반환 ", tags = "포인트 정산", description =
        "멘티로 부터 받은 포인트 내역을 반환합니다. <br>"
            + "참고 DB : voltHistory")
    @GetMapping("/settle/mentorUuid={mentorUuid}/points")
    public BaseResponse<VoltHistoryDto> getPoints(
        @PathVariable("mentorUuid") String mentorUuid
    ) {
        log.info("start of getPoints , mentorUuid {}", mentorUuid);

        return new BaseResponse<>(settlePointsService.getVoltHistory(mentorUuid));
    }

    @Operation(summary = "기간 별 정산 내역 반환 ", tags = "포인트 정산",
        description = "기간 별 정산 내역을 반환합니다.<br>"
            + "날짜 형식 : 20240101 (8자리) <br>"
            + "- money의 경우 현재 1volt 당 100원 <br>"
            + "수수료 10%로 계산하여 반환합니다. <br>"
            + "- 정산 status는 PROCEEDING, COMPLETED 두 가지 상태로 관리됩니다.")
    @GetMapping("/settle/points")
    public BaseResponse<ExchangeDto> getPointsBetweenDates(
        @RequestParam(value = "startDate", required = false) String startDate,
        @RequestParam(value = "endDate", required = false) String endDate,
        @RequestParam(value = "mentorUuid", required = true) String mentorUuid
    ) {
        log.info("start of getPointsBetweenDates");
        return new BaseResponse<>(settlePointsService.getExchange(startDate, endDate, mentorUuid));
    }

    @Operation(summary = "멘티의 결제 or 충전 내역 반환 ", tags = "포인트 내역",
        description = "date(연,월,일), 포인트, source_table(volt_history,payment_info) 로 반환합니다.")
    @GetMapping("/points/history")
    public BaseResponse<List<PaymentResponseDto>> getPointHistory(
        @RequestParam("menteeUuid") String menteeUuid,
        @RequestParam("size") Integer size,
        @RequestParam("page") Integer page

    ) {
        log.info("start of getPointHistory");

        return new BaseResponse<>(pointHistoryService.getPointHistory(menteeUuid, size, page));
    }


}
