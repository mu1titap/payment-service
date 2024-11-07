package com.multitap.payment.api.presentation;

import com.multitap.payment.api.application.KakaoPayService;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.vo.KakaoPayRequestVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    @PostMapping("/ready")
    public void paymentReady(KakaoPayRequestVo vo){
        kakaoPayService.createKakaoPay(KakaoPayRequestDto.fromVo(vo));

    }

}
