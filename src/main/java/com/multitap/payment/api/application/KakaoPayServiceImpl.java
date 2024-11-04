package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.infrastructure.KakaoPayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KakaoPayServiceImpl implements KakaoPayService  {

    private final KakaoPayRepository kakaoPayRepository;

    @Override
    public void createKakaoPay(KakaoPayRequestDto kakaoPayRequestDto) {
        kakaoPayRepository.save(kakaoPayRequestDto.toEntity());
    }
}
