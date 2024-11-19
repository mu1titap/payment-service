package com.multitap.payment.api.presentation;

import com.multitap.payment.api.application.UserServiceClient;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.common.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v2/payment")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final UserServiceClient userServiceClient;

    // ts statuscode , 트러블슈팅, jackson과의 호환성
    // ts 와일드카드
    // 팀원 공유
    @PostMapping("/test")
    public BaseResponse<?> testPayment() {
        BaseResponse<Void> response =
            userServiceClient.updatePoints(UserReqDto.builder()
                .userUuid("test")
                .pointQuantity(100)
                .build());

        log.info("in testPayment {} , ", response.toString());
        return new BaseResponse<>();
    }


}
