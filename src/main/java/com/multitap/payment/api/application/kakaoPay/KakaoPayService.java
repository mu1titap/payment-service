package com.multitap.payment.api.application.kakaoPay;

import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.dto.out.KakaoPayResponseDto;

public interface KakaoPayService {

    KakaoPayResponseDto kakaoPayReady(KakaoPayRequestDto kakaoPayRequestDto);

    KakaoPayApproveResponseDto kakaoPayApprove(String pgToken);

//    void addPoint(UserReqDto userReqDto, KakaoPayApproveRequestVo kakaoPayApproveRequestVo);
}
