package com.multitap.payment.api.common.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() throws InterruptedException {
        return requestTemplate -> requestTemplate.header("content-type", "application/json");
    }

}
