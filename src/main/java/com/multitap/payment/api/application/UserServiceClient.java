package com.multitap.payment.api.application;

import com.multitap.payment.api.common.config.UserServiceClientConfig;
import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.common.entity.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

// ToDo discoveryService 이용해서 host url 가져오기
// TODO pull 받은 다음 localhost에서 테스트 하기
@Service
@FeignClient(name = "user-service", url = "http://localhost:3929", configuration = UserServiceClientConfig.class)
// url : test
public interface UserServiceClient {

    @PostMapping("api/v1/member/points/update")
        // to be corrected
    BaseResponse<Void> updatePoints(UserReqDto userReqDto);

    @PostMapping("api/v1/member/points/restore")
    Boolean restorePoints(UserReqDto userReqDto);    // 원래대로 돌리다


}
