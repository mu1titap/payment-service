package com.multitap.payment.api.presentation;

import com.multitap.payment.api.vo.KakaoPayRequestVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @PostMapping("/ready")
    public KakaoPayRequestVo paymentReady(KakaoPayRequestVo vo){

    }

}
