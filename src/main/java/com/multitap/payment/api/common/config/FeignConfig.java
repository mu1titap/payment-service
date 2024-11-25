package com.multitap.payment.api.common.config;

import com.multitap.payment.api.application.UserServiceClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// 환경에 따라 다른 Feign Url 값 설정하기 위한 파일
@Configuration
public class FeignConfig {

    @Bean
    @Profile("local")
    public UserServiceClient localUserServiceClient() {
        return Feign.builder()
            .decoder(new JacksonDecoder())
            .target(UserServiceClient.class, "http://localhost:3929");
    }

    // prod 환경 없을 시, url 설정 x. 
    // url 설정 없을 시, 이름으로 service 찾음
    @Bean
    @Profile("prod")
    public UserServiceClient prodUserServiceClient() {
        return Feign.builder()
            .decoder(new JacksonDecoder())
            .target(UserServiceClient.class, "");
    }
}
