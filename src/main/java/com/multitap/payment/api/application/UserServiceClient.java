package com.multitap.payment.api.application;

import com.multitap.payment.api.common.config.UserServiceClientConfig;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.common.entity.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;


@Service
@FeignClient(name = "user-service", configuration = UserServiceClientConfig.class)
public interface UserServiceClient {

    @PostMapping("api/v1/member/points/add")
        // to be corrected
    BaseResponse<Void> updatePoints(UserReqDto userReqDto);

    @PostMapping("api/v1/member/points/add")
    BaseResponse<Void> restorePoints(UserReqDto userReqDto);    // 음수 값 줘서 원래대로 돌리다


}
