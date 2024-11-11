package com.multitap.payment.api.presentation;

import com.multitap.payment.api.application.KakaoPayService;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.vo.KakaoPayRequestVo;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    @PostMapping("/ready")
    @Transactional
    public String paymentReady(KakaoPayRequestVo vo){
        KakaoPayRequestDto kakaoPayRequestDto = KakaoPayRequestDto.fromVo(vo);
        // 결제 요청
        String next_redirect_pc_url =
        kakaoPayService.kakaoPayReady(kakaoPayRequestDto);
        // 요청 정보 DB 저장
        kakaoPayService.createKakaoPay(kakaoPayRequestDto);
        
        return next_redirect_pc_url;
    }

}
