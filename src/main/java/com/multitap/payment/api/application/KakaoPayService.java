package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.dto.out.KakaoPayResponseDto;
import com.multitap.payment.api.vo.in.KakaoPayApproveRequestVo;

public interface KakaoPayService {

    KakaoPayResponseDto kakaoPayReady(KakaoPayRequestDto kakaoPayRequestDto);

    KakaoPayApproveResponseDto kakaoPayApprove(String pgToken);


    void addPoint(UserReqDto userReqDto, KakaoPayApproveRequestVo kakaoPayApproveRequestVo);
}
