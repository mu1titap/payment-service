package com.multitap.payment.api.application;


import com.multitap.payment.api.dto.in.UserReqDto;
import com.multitap.payment.common.entity.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


// serviceClient 변경 시 git ignore 주석 하고 push하기 //
@FeignClient(name = "member-service", url = "${member-service.base-url}")
public interface UserServiceClient {

    @PutMapping("/api/v1/member/points/add")
    BaseResponse<Void> addPoints(@RequestBody UserReqDto userReqDto);

    @PutMapping("/api/v1/member/points/use")
    BaseResponse<Boolean> usePoints(@RequestParam("userUuid") String userUuid,
        @RequestParam("pointPrice") Integer pointPrice);

}







