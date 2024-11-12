package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.common.Exception.BaseException;
import com.multitap.payment.api.dto.in.KakaoPayApproveRequestDto;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.out.KakaoPayResponseDto;
import com.multitap.payment.api.infrastructure.KakaoPayRepository;
import com.multitap.payment.common.entity.BaseResponseStatus;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoPayServiceImpl implements KakaoPayService  {

    private final KakaoPayRepository kakaoPayRepository;

    @Value("${kakao.api.secret-key}")
    private String KAKAO_SECRET_KEY;

    private final String KAKAO_PAY_HOST_URL = "https://open-api.kakaopay.com";

    @Override
    public void createKakaoPay(KakaoPayApproveResponseDto kakaoPayApproveResponseDto) {
        kakaoPayRepository.save(kakaoPayApproveResponseDto.toEntity());
    }

    @Override
    public KakaoPayResponseDto kakaoPayReady(KakaoPayRequestDto kakaoPayRequestDto) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory()); // 정확한 에러 파악을 위해 생성
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + KAKAO_SECRET_KEY); // API key
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        Map<String, String> payParams = new HashMap<>();
        payParams.put("cid", kakaoPayRequestDto.getCid());
        payParams.put("partner_order_id", kakaoPayRequestDto.getPartnerOrderId());
        payParams.put("partner_user_id", kakaoPayRequestDto.getPartnerUserId());
        payParams.put("item_name", kakaoPayRequestDto.getItemName());
        payParams.put("quantity", "3");
        payParams.put("total_amount", "14400");
        payParams.put("tax_free_amount", "3000");
        payParams.put("approval_url", kakaoPayRequestDto.getApprovalUrl());
        payParams.put("fail_url", kakaoPayRequestDto.getFailUrl());
        payParams.put("cancel_url", kakaoPayRequestDto.getCancelUrl());

        log.info("payParams : {}", payParams);
        HttpEntity<Map<String,String>> requestEntity = new HttpEntity<>(payParams,headers);

        String requestUrl = KAKAO_PAY_HOST_URL + "/online/v1/payment/ready";
        KakaoPayResponseDto kakaoPayResponseDto = restTemplate.postForObject(
            requestUrl,
            requestEntity,
            KakaoPayResponseDto.class
        );

        log.info("ResponseDto : {}", kakaoPayResponseDto);

        assert kakaoPayResponseDto != null : new BaseException(BaseResponseStatus.NO_KAKAOPAY_RESPONSE);
        kakaoPayResponseDto.setPartnerOrderIdToResponse(kakaoPayRequestDto.getPartnerOrderId());

        return kakaoPayResponseDto;

    }

    @Override
    public KakaoPayApproveResponseDto kakaoPayApprove(
        KakaoPayApproveRequestDto kakaoPayApproveRequestDto){

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + KAKAO_SECRET_KEY); // API key
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        Map<String, String> payParams = new HashMap<>();
//        payParams.put("cid", kakaoPayApproveRequestDto.getCid());
        payParams.put("cid", "TC0ONETIME");
        payParams.put("tid", kakaoPayApproveRequestDto.getTid());
        payParams.put("partner_order_id", kakaoPayApproveRequestDto.getPartnerOrderId());
        payParams.put("partner_user_id", kakaoPayApproveRequestDto.getPartnerUserId());
        payParams.put("pg_token", kakaoPayApproveRequestDto.getPgToken());

        log.info("payParams: {}" + payParams);
        HttpEntity<Map<String,String>> requestEntity = new HttpEntity<>(payParams,headers);

        log.info("cid: {}" , payParams.get("cid"));

        String requestUrl = KAKAO_PAY_HOST_URL + "/online/v1/payment/approve";
        KakaoPayApproveResponseDto kakaoPayApproveResponseDto = restTemplate.postForObject(
            requestUrl,
            requestEntity,
            KakaoPayApproveResponseDto.class
        );
        assert kakaoPayApproveResponseDto != null;
        log.info("kakaoPayApproveResponse {}", kakaoPayApproveResponseDto.toString());
        return kakaoPayApproveResponseDto;
        
        // todo dto로 바꾸기
        // todo db 저장하기
        // todo method 화 => ctrl alt m 하기


    }

}
