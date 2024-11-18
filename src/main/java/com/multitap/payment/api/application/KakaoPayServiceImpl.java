package com.multitap.payment.api.application;

import com.multitap.payment.api.common.PaymentType;
import com.multitap.payment.api.dto.in.KakaoPayApproveRequestDto;
import com.multitap.payment.api.dto.in.KakaoPayRequestDto;
import com.multitap.payment.api.dto.in.PaymentInfoDto;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.api.dto.out.KakaoPayApproveResponseDto;
import com.multitap.payment.api.dto.out.KakaoPayResponseDto;
import com.multitap.payment.api.infrastructure.KakaoPayRepository;
import com.multitap.payment.api.infrastructure.PaymentInfoRepository;
import com.multitap.payment.api.kafka.KafkaProducer;
import com.multitap.payment.api.vo.PaymentInfoVo;
import com.multitap.payment.common.Exception.BaseException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoPayServiceImpl implements KakaoPayService {

    private final KakaoPayRepository kakaoPayRepository;
    private final PaymentInfoRepository paymentInfoRepository;
    private final KafkaProducer kafkaProducer;
    private final UserServiceClient userServiceClient;


    private final PaymentService paymentService;
    @Value("${kakao.api.secret-key}")
    private String KAKAO_SECRET_KEY;

    private final String KAKAO_PAY_HOST_URL = "https://open-api.kakaopay.com";


    @Override
    public void createKakaoPay(KakaoPayApproveResponseDto kakaoPayApproveResponseDto) {
        kakaoPayRepository.save(kakaoPayApproveResponseDto.toEntity());
    }

    @Override
    public void savePaymentInfo(PaymentInfoVo paymentInfoVo) {

    }


    @Override
    @Transactional
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
        payParams.put("quantity", "3");
        payParams.put("total_amount", "14400");
        payParams.put("tax_free_amount", "3000");
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

        return kakaoPayResponseDto;

    }

    @Override
    public KakaoPayApproveResponseDto kakaoPayApprove(
        KakaoPayApproveRequestDto kakaoPayApproveRequestDto) {

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
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(payParams, headers);

        log.info("cid: {}", payParams.get("cid"));

        String requestUrl = KAKAO_PAY_HOST_URL + "/online/v1/payment/approve";
        KakaoPayApproveResponseDto kakaoPayApproveResponseDto = restTemplate.postForObject(
            requestUrl,
            requestEntity,
            KakaoPayApproveResponseDto.class
        );
        assert kakaoPayApproveResponseDto != null;
        log.info("kakaoPayApproveResponse {}", kakaoPayApproveResponseDto.toString());
        return kakaoPayApproveResponseDto;

    }

    @Transactional
    @Override
    public void paymentProcess(KakaoPayApproveRequestDto kakaoPayApproveRequestDto,
        UserReqDto userReqDto) {

        // 1. Feign Client : 유저 포인트 보유량 update
        // 1.1 오류 발생 확인 시
        if (userServiceClient.updatePoints(userReqDto)) {
            throw new BaseException(BaseResponseStatus.POINT_UPDATE_FAILED);
        }

        log.info("Successfully point updated!");

        try {
            // 승인 요청
            KakaoPayApproveResponseDto kakaoPayApproveResponseDto =
                kakaoPayApprove(kakaoPayApproveRequestDto);
            // 승인 정보 DB 저장
            createKakaoPay(kakaoPayApproveResponseDto);
            // 회원 볼트 결제 내역

            // 결제 정보 저장
            PaymentInfoDto paymentInfoDto = PaymentInfoDto.builder()
                .menteeUuid(userReqDto.getUserUuid())
                .volt(kakaoPayApproveResponseDto.getQuantity())
                .type(PaymentType.KAKAO_PAY) // 다른 결제 type 생길 시 변경
                .cash(kakaoPayApproveResponseDto.getAmount().getTotal())
                .build();
            paymentInfoRepository.save(paymentInfoDto.toEntity());
        } catch (Exception e) { // 결제 정보 오류
            log.error("Payment process error: {}", e.getMessage());
            // 원래 상태로 돌리도록 요청
            // 1.1 원래대로 되는지 확인
            if (userServiceClient.restorePoints(userReqDto)) {
                throw new BaseException(BaseResponseStatus.PAYMENT_PROCESS_ERROR);
            }
            // 1.2 오류 발생 시
            // 관리자 통해 확인하도록
            throw new BaseException(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        }


    }


}
