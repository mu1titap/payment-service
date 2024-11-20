package com.multitap.payment.api.presentation;

import com.multitap.payment.api.application.KakaoPayService;
import com.multitap.payment.api.dto.in.KakaoPayApproveRequestDto;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.vo.KakaoPayApproveRequestVo;
import com.multitap.payment.api.vo.KakaoPayRequestVo;
import com.multitap.payment.api.vo.KakaoPayResponseVo;
import com.multitap.payment.common.Exception.BaseException;
import com.multitap.payment.common.entity.BaseResponse;
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

    @PostMapping("/ready")
    @Operation(summary = "결제 준비 요청")
    public BaseResponse<KakaoPayResponseVo> paymentReady(@RequestBody KakaoPayRequestVo vo) {
        log.info("paymentRead");
        KakaoPayRequestDto kakaoPayRequestDto = KakaoPayRequestDto.from(vo);

        // 결제 요청
        return new BaseResponse<>(kakaoPayService.kakaoPayReady(kakaoPayRequestDto).toVo());
    }

    @Operation(summary = "결제 승인 요청")
    @PostMapping("/approve")
    public BaseResponse<KakaoPayApproveResponseDto> paymentApprove(
        @RequestParam("uuid") String memberuuid,
        @RequestBody KakaoPayApproveRequestVo kakaoPayApproveRequestVo
    ) {   // TODO check
        log.info("start of payment approve");
        UserReqDto userReqDto = UserReqDto.builder()
            .userUuid(memberuuid)
            .pointQuantity(kakaoPayApproveRequestVo.getQuantity())
            .build();

        // paymentProcess 에서 전부 처리하기
        try {
            kakaoPayService.paymentProcess(
                KakaoPayApproveRequestDto.from(kakaoPayApproveRequestVo), userReqDto);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (Exception e) {
            log.error("paymentProcess error : {}", e.getMessage());
            throw new RuntimeException("paymentProcess error");
        }
        return new BaseResponse<>();
    }


}
