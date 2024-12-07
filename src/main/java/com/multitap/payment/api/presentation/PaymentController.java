package com.multitap.payment.api.presentation;

import com.multitap.payment.api.application.SessionPay.SessionPaymentService;
import com.multitap.payment.api.application.SettlePay.SettlePointsService;
import com.multitap.payment.api.application.kakaoPay.KakaoPayService;
import com.multitap.payment.api.dto.in.ExchangePointsDto;
import com.multitap.payment.api.dto.in.KakaoPayApproveRequestDto;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.in.SessionPaymentDto;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.vo.ExchangePointsVo;
import com.multitap.payment.api.vo.SessionPaymentVo;
import com.multitap.payment.api.vo.in.KakaoPayApproveRequestVo;
import com.multitap.payment.api.vo.in.KakaoPayRequestVo;
import com.multitap.payment.api.vo.out.KakaoPayResponseVo;
import com.multitap.payment.common.entity.BaseResponse;
import com.multitap.payment.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Operation(summary = "포인트 정산", tags = "포인트 정산", description = "포인트 정산을 진행합니다. <br>"
        + " 2차 인증이 진행된 이후 요청할 수 있습니다.")
    @PostMapping("/settle")
    public BaseResponse<Void> settlePoints(
        @RequestBody ExchangePointsVo settlePointVo
    ) {
        log.info("start of settlePoints");

        if (!settlePointsService.settlePoints(ExchangePointsDto.of(settlePointVo))) {
            return new BaseResponse<>(BaseResponseStatus.POINT_UPDATE_FAILED);
        }
        return new BaseResponse<>();
    }

    @Operation(summary = "2차 인증 번호 발송", tags = "포인트 정산", description = "추가 인증을 위한 인증번호를 발송합니다 <br>")
    @PostMapping("/settle/send/random-number")
    public BaseResponse<Void> sendRandomNumber(
        @RequestParam("userUuid") String userUuid
    ) {
        log.info("start of sendRandomNum");

        settlePointsService.sendRandomNumber(userUuid);

        return new BaseResponse<>();
    }

    @Operation(summary = "2차 인증 번호 동일 여부 확인", tags = "포인트 정산", description = "발송한 인증번호와 일치하는지 확인합니다. <br>")
    @PostMapping("/settle/check/random-number")
    public BaseResponse<Boolean> checkNumber(@RequestParam("userUuid") String userUuid,
        @RequestParam("insertedNumber") String insertedNumber
    ) {
        log.info("start of checkNum");

        return new BaseResponse<>(settlePointsService.checkRandomNumber(userUuid, insertedNumber));
    }

    @Operation(summary = "정산 페이지 이동 시 요청", tags = "포인트 정산", description = "random 번호 및 인증된 회원 목록을 redis에서 삭제합니다. <br>")
    @PostMapping("/settle/redirect-page")
    public void redirectPage(@RequestParam("userUuid") String userUuid
    ) {
        log.info("start of redirectPage");
        settlePointsService.deleteRandomNumber(userUuid);
        settlePointsService.deleteVerifiedUser(userUuid);

    }


}
