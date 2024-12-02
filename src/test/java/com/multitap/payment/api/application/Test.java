package com.multitap.payment.api.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.multitap.payment.api.domain.BankCode;
import com.multitap.payment.api.dto.in.ExchangePointsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class Test {


    @Autowired
    private SettlePointsService settlePointsService;

    @MockBean
    private UserServiceClient userServiceClient;


    @org.junit.Test
    public void test() {
        ExchangePointsDto exchangePointsDto = ExchangePointsDto.builder()
            .mentorUuid("2323")
            .points(1000)
            .account("1234")
            .bankCode(BankCode.KOREA_NH_BANK)
            .build();

        // Mock 동작 정의
//        when(userServiceClient.usePoints("2323", 1000))
//            .thenReturn(new ApiResponse<>(true)); // 원하는 값 반환 설정

        // when
        boolean result = userServiceClient.usePoints("2323", 1000).result();

        // then
        assertThat(result).isEqualTo(true);
    }


}
