package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.UserReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

// ToDo discoveryService 이용해서 host url 가져오기
// TODO pull 받은 다음 localhost에서 테스트 하기
@Service
@FeignClient(name = "user-service", url = "http://localhost:8080") // url : test
public interface UserServiceClient {

    @PostMapping("/points/update")
        // to be corrected
    Boolean updatePoints(UserReqDto userReqDto);

    @PostMapping("/points/restore")
    Boolean restorePoints(UserReqDto userReqDto);


}
