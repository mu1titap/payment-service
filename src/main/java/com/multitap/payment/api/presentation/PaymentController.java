package com.multitap.payment.api.presentation;

import com.multitap.payment.api.application.SessionPay.SessionPaymentService;
import com.multitap.payment.api.application.SettlePay.SettlePointsService;
import com.multitap.payment.api.application.kakaoPay.KakaoPayService;
import com.multitap.payment.api.dto.in.ExchangePointsDto;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.in.SessionPaymentDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.vo.ExchangePointsVo;
import com.multitap.payment.api.vo.SessionPaymentVo;
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
    @Operation(summary = "결제 준비 요청", tags = "카카오페이 결제"
        , description = "cid : TC0ONETIME(고정), partner_order_id(가맹점번호): back에서 생성 ,<br>"
        + " partner_user_id(유저uuid) : <br>"
        + " item_name : 상품명 <br> "
        + " quantity : 수량 <br> "
        + " total_amount : 총액 <br>"
        + " tax_free_amount : 비과세액 <br> "
        + " approval_url : 결제성공시 리다이렉트 url <br>"
        + " cancel_url : 결제취소시 리다이렉트 url <br> "
        + " fail_url : 결제실패시 리다이렉트 url")
    public BaseResponse<KakaoPayResponseVo> paymentReady(@RequestBody KakaoPayRequestVo vo) {
        log.info("paymentRead");
        KakaoPayRequestDto kakaoPayRequestDto = KakaoPayRequestDto.from(vo);
        // 결제 요청
        return new BaseResponse<>(kakaoPayService.kakaoPayReady(kakaoPayRequestDto).toVo());
    }

    @Operation(summary = "결제 승인 요청", tags = "카카오페이 결제"
        , description = "pg_token 요청 시 최종 결제 승인이 됩니다.")
    @PostMapping("/approve")
    public BaseResponse<KakaoPayApproveResponseDto> paymentApprove(
        @RequestParam("pg_token") String pgToken

    ) {
        log.info("start of payment approve");
        kakaoPayService.kakaoPayApprove(pgToken);
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
        + "<bankCode> <br>"
        + "KOREA_SC : SC 은행 <br>"
        + "KOREA_SHINHAN : 신한은행 <br>"
        + "KOREA_KB : KB은행 <br>"
        + "KOREA_HANA : 하나은행 <br>"
        + "KOREA_IBK : IBK 은행 <br>"
        + "KOREA_NH_BANK : 농협은행 <br>"
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

        // todo : 2차 인증 번호 확인

        return new BaseResponse<>();
    }


    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/redisTest")
    public ResponseEntity<?> addRedisKey(@RequestBody ViewInfoModel vo) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        log.info("vo: {}", vo.toString());
        valueOperations.set(vo.getCallId(), vo.getOpenedAt());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/redisTest/{key}")
    public ResponseEntity<?> getRedisKey(@PathVariable("key") String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(key);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @GetMapping("/redisTest/check/{key}")
    public ResponseEntity<Boolean> checkRandNum(@RequestParam("phoneNumber") String phoneNumber,
        @RequestParam("insertedNumber") String insertedNumber) {
        return new ResponseEntity<>(
            settlePointsService.checkRandomNumber(phoneNumber, insertedNumber), HttpStatus.OK);
    }


}
