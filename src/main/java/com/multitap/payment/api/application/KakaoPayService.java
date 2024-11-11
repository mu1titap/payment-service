package com.multitap.payment.api.application;

import com.multitap.payment.api.domain.KakaoPay;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.out.KakaoPayResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoPayService  {
    void createKakaoPay(KakaoPayRequestDto kakaoPayRequestDto);

    String kakaoPayReady(KakaoPayRequestDto kakaoPayRequestDto);
}
