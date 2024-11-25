package com.multitap.payment.api.common.config;

import com.multitap.payment.common.Exception.BaseException;
import com.multitap.payment.common.entity.BaseResponseStatus;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;


@Slf4j
public class TestDecoderConfig {

    @Bean
    public ErrorDecoder worldAuthErrorDecoder() {
        return this::decode;
    }

    private Exception decode(String methodKey, Response response) {
        String responseBody = extractResponseBody(response);

        log.info("response: {}", response);
        log.info("responseBody: {}", responseBody);
        log.info("response.status: {}", response.status());

        // todo 보편적으로 처리할 수 있도록 if 분기처리
        // json화 해서 메시지 확인 or code 확인 해서
        if (HttpStatus.BAD_REQUEST.value() == response.status()) {
            log.info("here in decode if statement");
            return new BaseException(BaseResponseStatus.NOT_ENOUGH_POINT);
        } else {
            return new Exception("실패 사유를 모르는 실패");
        }
    }

    private String extractResponseBody(Response response) {
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("변환 실패");
        }
    }

}
