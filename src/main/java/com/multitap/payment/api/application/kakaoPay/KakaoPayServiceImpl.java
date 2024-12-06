package com.multitap.payment.api.application.kakaoPay;

import com.multitap.payment.api.application.UserServiceClient;
import com.multitap.payment.api.domain.enum_file.PaymentType;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.in.PaymentInfoDto;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.dto.out.KakaoPayResponseDto;
import com.multitap.payment.api.infrastructure.KakaoPayRepository;
import com.multitap.payment.api.infrastructure.PaymentInfoRepository;
import com.multitap.payment.common.Exception.BaseException;
import com.multitap.payment.common.entity.BaseResponseStatus;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoPayServiceImpl implements KakaoPayService {

    private final KakaoPayRepository kakaoPayRepository;
    private final PaymentInfoRepository paymentInfoRepository;
    private final UserServiceClient userServiceClient;
    private final RedisTemplate<String, String> redisTemplates;


    @Value("${kakao.api.secret-key}")
    private String KAKAO_SECRET_KEY;

    private final String KAKAO_PAY_HOST_URL = "https://open-api.kakaopay.com";


    @Override
    public KakaoPayResponseDto kakaoPayReady(KakaoPayRequestDto kakaoPayRequestDto) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(
            new HttpComponentsClientHttpRequestFactory()); // 정확한 에러 파악을 위해 생성
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
        payParams.put("quantity", kakaoPayRequestDto.getQuantity().toString());
        payParams.put("total_amount", kakaoPayRequestDto.getTotalAmount().toString());
        payParams.put("tax_free_amount", kakaoPayRequestDto.getTaxFreeAmount().toString());
        payParams.put("approval_url", kakaoPayRequestDto.getApprovalUrl());
        payParams.put("fail_url", kakaoPayRequestDto.getFailUrl());
        payParams.put("cancel_url", kakaoPayRequestDto.getCancelUrl());

        log.info("payParams : {}", payParams);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(payParams, headers);

        String requestUrl = KAKAO_PAY_HOST_URL + "/online/v1/payment/ready";
        KakaoPayResponseDto kakaoPayResponseDto = restTemplate.postForObject(
            requestUrl,
            requestEntity,
            KakaoPayResponseDto.class
        );

        log.info("ResponseDto : {}", kakaoPayResponseDto);

        assert kakaoPayResponseDto != null : new BaseException(
            BaseResponseStatus.NO_KAKAOPAY_RESPONSE);
        kakaoPayResponseDto.setPartnerOrderIdToResponse(kakaoPayRequestDto.getPartnerOrderId());

        // redis 추가
        ValueOperations<String, String> valueOperations = redisTemplates.opsForValue();
        valueOperations.set("cid", kakaoPayRequestDto.getCid());
        valueOperations.set("tid", kakaoPayResponseDto.getTid());
        valueOperations.set("partner_order_id", kakaoPayResponseDto.getPartnerOrderId());
        valueOperations.set("partner_user_id", kakaoPayRequestDto.getPartnerUserId());
        valueOperations.set("quantity", Integer.toString(kakaoPayRequestDto.getQuantity()));

        return kakaoPayResponseDto;

    }

    @Override
    @Transactional
    public KakaoPayApproveResponseDto kakaoPayApprove(
        String pgToken) {

        // redis
        ValueOperations<String, String> valueOperations = redisTemplates.opsForValue();
        ;

        log.info("start of kakaoPayApprove");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + KAKAO_SECRET_KEY); // API key
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        Map<String, String> payParams = new HashMap<>();
        payParams.put("cid", valueOperations.get("cid"));
        payParams.put("tid", valueOperations.get("tid"));
        payParams.put("partner_order_id", valueOperations.get("partner_order_id"));
        payParams.put("partner_user_id", valueOperations.get("partner_user_id"));
        payParams.put("pg_token", pgToken);

        log.info("payParams: {}", payParams);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(payParams, headers);

        log.info("cid: {}", payParams.get("cid"));

        String requestUrl = KAKAO_PAY_HOST_URL + "/online/v1/payment/approve";
        KakaoPayApproveResponseDto kakaoPayApproveResponseDto = restTemplate.postForObject(
            requestUrl,
            requestEntity,
            KakaoPayApproveResponseDto.class
        );
        if (kakaoPayApproveResponseDto == null) {
            throw new AssertionError();
        }
        log.info("kakaoPayApproveResponse {}", kakaoPayApproveResponseDto.toString());

        // 결제 요청 저장
        kakaoPayRepository.save(kakaoPayApproveResponseDto.toEntity());
        PaymentInfoDto paymentInfoDto = PaymentInfoDto.builder()
            .menteeUuid(kakaoPayApproveResponseDto.getPartner_user_id())
            .volt(kakaoPayApproveResponseDto.getQuantity())
            .type(PaymentType.KAKAO_PAY) // 다른 결제 type 생길 시 변경
            .cash(kakaoPayApproveResponseDto.getAmount().getTotal())
            .build();
        paymentInfoRepository.save(paymentInfoDto.toEntity());
        log.info("Payment information saved successfully");
        log.info("request add user point");

        UserReqDto userReqDto = UserReqDto.builder()
            .userUuid(kakaoPayApproveResponseDto.getPartner_user_id())
            .pointQuantity(kakaoPayApproveResponseDto.getAmount().getTotal())
            .build();
        log.info("userReqDto: {}", userReqDto.toString());
        userServiceClient.addPoints(userReqDto);
        log.info("after userServiceClient.addPoints");

        return kakaoPayApproveResponseDto;

    }

//    @Override
//    public void addPoint(UserReqDto userReqDto, KakaoPayApproveRequestVo kakaoPayApproveRequestVo) {
//        log.info("start of addPoint at serviceImpl");
//        if (!kakaoPayRepository.existsByCid(kakaoPayApproveRequestVo.getCid())) {
//            throw new BaseException(BaseResponseStatus.NO_KAKOPAY_PAYMENT);
//        } // 중복 결제 방지. kakoPay 결제 확인 시 만 결제하도록
//        userServiceClient.addPoints(userReqDto);
//        log.info("Successfully point updated!");
//    }


}
