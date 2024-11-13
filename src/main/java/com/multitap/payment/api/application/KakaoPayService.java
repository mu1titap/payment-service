package com.multitap.payment.api.application;

import com.multitap.payment.api.domain.KakaoPay;
import com.multitap.payment.api.dto.in.KakaoPayApproveRequestDto;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.in.PaymentInfoDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.dto.out.KakaoPayResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoPayService  {
    void createKakaoPay(KakaoPayApproveResponseDto kakaoPayResponseDto);

    KakaoPayResponseDto kakaoPayReady(KakaoPayRequestDto kakaoPayRequestDto);

    KakaoPayApproveResponseDto kakaoPayApprove(KakaoPayApproveRequestDto kakaoPayApproveRequestDto);

    void savePaymentInfo(PaymentInfoDto paymentInfoDto);

    void paymentProcess(KakaoPayApproveRequestDto kakaoPayApproveRequestDto);

}
