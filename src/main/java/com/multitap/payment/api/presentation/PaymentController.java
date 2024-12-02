package com.multitap.payment.api.presentation;

import com.multitap.payment.api.application.KakaoPayService;
import com.multitap.payment.api.application.SessionPaymentService;
import com.multitap.payment.api.application.SettlePointsService;
import com.multitap.payment.api.dto.in.KakaoPayApproveRequestDto;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.in.SessionPaymentDto;
import com.multitap.payment.api.dto.in.SettlePointsDto;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.dto.out.VoltHistoryDto;
import com.multitap.payment.api.vo.KakaoPayApproveRequestVo;
import com.multitap.payment.api.vo.KakaoPayRequestVo;
import com.multitap.payment.api.vo.KakaoPayResponseVo;
import com.multitap.payment.api.vo.SessionPaymentVo;
import com.multitap.payment.api.vo.SettlePointsVo;
import com.multitap.payment.common.entity.BaseResponse;
import com.multitap.payment.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaymentController {

    private final KakaoPayService kakaoPayService;
    private final SessionPaymentService sessionPaymentService;
    private final SettlePointsService settlePointsService;

    @PostMapping("/ready")
    @Operation(summary = "결제 준비 요청", tags = "카카오페이 결제")
    public BaseResponse<KakaoPayResponseVo> paymentReady(@RequestBody KakaoPayRequestVo vo) {
        log.info("paymentRead");
        KakaoPayRequestDto kakaoPayRequestDto = KakaoPayRequestDto.from(vo);
        // 결제 요청
        return new BaseResponse<>(kakaoPayService.kakaoPayReady(kakaoPayRequestDto).toVo());
    }

    @Operation(summary = "결제 승인 요청", tags = "카카오페이 결제")
    @PostMapping("/approve")
    public BaseResponse<KakaoPayApproveResponseDto> paymentApprove(
        @RequestParam("uuid") String memberuuid,
        @RequestBody KakaoPayApproveRequestVo kakaoPayApproveRequestVo

    ) {
        log.info("start of payment approve");
        kakaoPayService.kakaoPayApprove(KakaoPayApproveRequestDto.from(kakaoPayApproveRequestVo),
            memberuuid);
        return new BaseResponse<>();
    }

    @Operation(summary = "카카오페이 결제 정보 확인 후 포인트 증가 요청", tags = "포인트",
        description = "카카오페이 결제 정보 확인 후 포인트 증가 요청합니다. <br>  cid 값으로 확인하며 기본 cid 값은 TC0ONETIME 입니다.")
    @PostMapping("/point/add")
    public BaseResponse<Void> addPoint(
        @RequestParam("uuid") String memberuuid,
        @RequestBody KakaoPayApproveRequestVo kakaoPayApproveRequestVo
    ) {
        log.info("start of addPoint at controller");
        UserReqDto userReqDto = UserReqDto.builder()
            .userUuid(memberuuid)
            .pointQuantity(kakaoPayApproveRequestVo.getQuantity())
            .build();

        kakaoPayService.addPoint(userReqDto, kakaoPayApproveRequestVo);
        return new BaseResponse<>();
    }

    @Operation(summary = "세션 결제", tags = "세션 결제", description = "멘티의 포인트 보유량은 감소하고 <br>"
        + "멘토의 포인트 보유량은 증가합니다.")
    @PostMapping("/session")
    public BaseResponse<Void> paymentSession(
        @RequestBody SessionPaymentVo sessionPaymentVo
    ) {
        log.info("start of paymentSession");
        log.info("sessionPaymentVo: {}", sessionPaymentVo.toString());
        sessionPaymentService.paySession(SessionPaymentDto.from(sessionPaymentVo));

        return new BaseResponse<>();
    }

    @Operation(summary = "포인트 정산", tags = "포인트 정산", description = "포인트 정산을 진행합니다.")
    @PostMapping("/settle")
    public BaseResponse<Void> settlePoints(
        @RequestBody SettlePointsVo settlePointVo
    ) {
        log.info("start of settlePoints");

        if (!settlePointsService.settlePoints(SettlePointsDto.of(settlePointVo))) {
            return new BaseResponse<>(BaseResponseStatus.POINT_UPDATE_FAILED);
        }
        return new BaseResponse<>();
    }

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

    @Operation(summary = "기간 별 정산 내역 반환 ", tags = "포인트 정산", description = "기간 별 정산 내역을 반환합니다.<br>"
        + "날짜 형식 : 20240101 (8자리)")
    @GetMapping("/settle/points")
    public BaseResponse<Void> getPointsBetweenDates(
        @RequestParam(value = "startDate", required = false) String startDate,
        @RequestParam(value = "endDate", required = false) String endDate
    ) {
        log.info("start of getPointsBetweenDates");

        return new BaseResponse<>();
    }


}
