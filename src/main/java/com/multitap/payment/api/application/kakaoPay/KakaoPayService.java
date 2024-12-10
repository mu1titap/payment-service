package com.multitap.payment.api.application.kakaoPay;

import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.dto.out.KakaoPayResponseDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface KakaoPayService {

    @Transactional
    KakaoPayResponseDto kakaoPayReady(KakaoPayRequestDto kakaoPayRequestDto);

    @Transactional
    KakaoPayApproveResponseDto kakaoPayApprove(String pgToken);

//    void addPoint(UserReqDto userReqDto, KakaoPayApproveRequestVo kakaoPayApproveRequestVo);
}
