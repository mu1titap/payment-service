package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.KakaoPayApproveRequestDto;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.dto.out.KakaoPayResponseDto;
import com.multitap.payment.api.vo.PaymentInfoVo;

public interface KakaoPayService  {
    void createKakaoPay(KakaoPayApproveResponseDto kakaoPayResponseDto);

    KakaoPayResponseDto kakaoPayReady(KakaoPayRequestDto kakaoPayRequestDto);

    KakaoPayApproveResponseDto kakaoPayApprove(KakaoPayApproveRequestDto kakaoPayApproveRequestDto);

    void savePaymentInfo(PaymentInfoVo paymentInfoVo);

    void paymentProcess(KakaoPayApproveRequestDto kakaoPayApproveRequestDto, UserReqDto userReqDto);

}
