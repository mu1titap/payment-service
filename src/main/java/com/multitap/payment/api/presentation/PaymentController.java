package com.multitap.payment.api.presentation;

import com.multitap.payment.api.application.KakaoPayService;
import com.multitap.payment.api.dto.in.KakaoPayApproveRequestDto;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.vo.KakaoPayApproveRequestVo;
import com.multitap.payment.api.vo.KakaoPayRequestVo;
import com.multitap.payment.api.vo.KakaoPayResponseVo;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    @PostMapping("/ready")
    @Transactional
    public KakaoPayResponseVo paymentReady(@RequestBody KakaoPayRequestVo vo){
        KakaoPayRequestDto kakaoPayRequestDto = KakaoPayRequestDto.from(vo);
        // 결제 요청
        return kakaoPayService.kakaoPayReady(kakaoPayRequestDto).toVo();
    }

    @Transactional
    @PostMapping("/approve")
    public KakaoPayApproveResponseDto paymentApprove(@RequestBody KakaoPayApproveRequestVo kakaoPayApproveRequestVo){
        KakaoPayApproveResponseDto kakaoPayApproveResponseDto =
            kakaoPayService.kakaoPayApprove(KakaoPayApproveRequestDto.from(kakaoPayApproveRequestVo));
        // DB 저장
        kakaoPayService.createKakaoPay(kakaoPayApproveResponseDto);
        return kakaoPayApproveResponseDto;

    }

}
