package com.multitap.payment.api.application;

import com.multitap.payment.api.common.config.TestDecoderConfig;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.common.entity.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Service
@FeignClient(name = "user-service", url = "http://localhost:3929", configuration = TestDecoderConfig.class)
public interface UserServiceClient {

    @PutMapping("api/v1/member/points/add")
    BaseResponse<Void> updatePoints(UserReqDto userReqDto);

    @PutMapping("api/v1/member/points/add")
    BaseResponse<Void> restorePoints(UserReqDto userReqDto);    // 음수 값 줘서 원래대로 돌리다

    @PutMapping("api/v1/member/points/use")
    BaseResponse<Object> usePoints(@RequestParam("userUuid") String userUuid,
        @RequestParam("pointPrice") Integer pointPrice);

}







