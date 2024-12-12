package com.multitap.payment.api.application;


import com.multitap.payment.api.common.config.TestDecoderConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


// serviceClient 변경 시 git ignore 주석 하고 push하기
@FeignClient(name = "auth-service", url = "${auth-service.base-url}"
    , configuration = TestDecoderConfig.class)
public interface AuthServiceClient { // test

    @GetMapping("api/v1/auth/member-email")
    String getUserEmail(@RequestParam("userUuid") String userUuid);


}







