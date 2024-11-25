package com.multitap.payment.common.Exception;


import com.multitap.payment.common.entity.BaseResponse;
import com.multitap.payment.common.entity.BaseResponseStatus;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    /**
     * Feign Client 호출 시 발생하는 예외 처리
     */

    @ExceptionHandler(FeignException.class)
    protected ResponseEntity<BaseResponse<Void>> handleFeignException(FeignException e) {
        BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.DISALLOWED_ACTION);
        log.error("FeignException: {}", e.getMessage());
//        int firstIndex = e.getMessage().lastIndexOf("message\":") + 1;
//        int lastIndex = e.getMessage().lastIndexOf(",\"code") + 1 - 5;
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        log.error("substring feignException: {} ", e.getMessage().substring(firstIndex, lastIndex));
        log.info("Response: {}", response.message());
        return new ResponseEntity<>(response, response.httpStatus());
    }


    /**
     * 발생한 예외 처리
     */

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<BaseResponse<Void>> BaseError(BaseException e) {
        BaseResponse<Void> response = new BaseResponse<>(e.getStatus());
        log.error("BaseException -> {}({})", e.getStatus(), e.getStatus().getMessage(), e);
        return new ResponseEntity<>(response, response.httpStatus());

    }

    /**
     * security 인증 에러 아이디가 없거나 비밀번호가 틀린 경우 AuthenticationManager 에서 발생
     *
     * @return FAILED_TO_LOGIN 에러 response
     */

//    @ExceptionHandler(RuntimeException.class)
//    protected ResponseEntity<BaseResponse<Void>> RuntimeError(RuntimeException e) {
//        BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
//        log.error("RuntimeException: {} ", e.getMessage());
//        log.info("RuntimeException {}", e.toString());
////        for (StackTraceElement s : e.getStackTrace()) {
////            System.out.println(s);
////        }
//        return new ResponseEntity<>(response, response.httpStatus());
//    }
    @ExceptionHandler
    protected ResponseEntity<BaseResponse<Void>> handleInterruptedException(
        InterruptedException e) {
        BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.REDIS_ERROR);
        log.error("InterruptedException from Redis: ", e);
        for (StackTraceElement s : e.getStackTrace()) {
            System.out.println(s);
        }
        return new ResponseEntity<>(response, response.httpStatus());
    }
}