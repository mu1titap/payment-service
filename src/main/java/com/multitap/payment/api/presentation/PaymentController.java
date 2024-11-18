package com.multitap.payment.api.presentation;

import com.multitap.payment.api.application.KakaoPayService;
import com.multitap.payment.api.dto.in.KakaoPayApproveRequestDto;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.vo.KakaoPayApproveRequestVo;
import com.multitap.payment.api.vo.KakaoPayRequestVo;
import com.multitap.payment.api.vo.KakaoPayResponseVo;
import com.multitap.payment.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    @PostMapping("/ready")
    @Operation(summary = "결제 준비 요청")
    public BaseResponse<KakaoPayResponseVo> paymentReady(@RequestBody KakaoPayRequestVo vo) {
        log.info("paymentRead");
        KakaoPayRequestDto kakaoPayRequestDto = KakaoPayRequestDto.from(vo);

        // 결제 요청
        return new BaseResponse<>(kakaoPayService.kakaoPayReady(kakaoPayRequestDto).toVo());
    }

    @Operation(summary = "결제 승인 요청")
    @PostMapping("/approve/uuid={uuid}")
    public BaseResponse<KakaoPayApproveResponseDto> paymentApprove(
        @RequestBody KakaoPayApproveRequestVo kakaoPayApproveRequestVo,
        @QueryParam("uuid") String memberuuid) {   // TODO check
        UserReqDto userReqDto = UserReqDto.builder()
            .userUuid(memberuuid)
            .pointQuantity(kakaoPayApproveRequestVo.getQuantity())
            .build();

        // paymentProcess 에서 전부 처리하기
        kakaoPayService.paymentProcess(
            KakaoPayApproveRequestDto.from(kakaoPayApproveRequestVo), userReqDto);

        // comment 예정
        KakaoPayApproveResponseDto kakaoPayApproveResponseDto =
            kakaoPayService.kakaoPayApprove(
                KakaoPayApproveRequestDto.from(kakaoPayApproveRequestVo));
        // DB 저장
        kakaoPayService.createKakaoPay(kakaoPayApproveResponseDto);
        //

        return new BaseResponse<>(kakaoPayApproveResponseDto);

    }


}
