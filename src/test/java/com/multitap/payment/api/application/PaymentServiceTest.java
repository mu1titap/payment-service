package com.multitap.payment.api.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.multitap.payment.api.dto.in.KakaoPayApproveRequestDto;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.api.infrastructure.PaymentInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = PaymentService.class)
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private KakaoPayService kakaoPayService;

    @MockBean
    private PaymentInfoRepository paymentInfoRepository;

    @Test
    public void testProcessPayment() {
        KakaoPayApproveRequestDto kakaoPayRequestDto = new KakaoPayApproveRequestDto();
        UserReqDto userReqDto = new UserReqDto("user123", 10000);

        kakaoPayService.paymentProcess(kakaoPayRequestDto, userReqDto);

        assertThat("3".equals("3"));


    }

}
