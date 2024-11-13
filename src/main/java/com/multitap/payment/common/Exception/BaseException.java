package com.multitap.payment.common.Exception;



import com.multitap.payment.common.entity.BaseResponseStatus;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    private final BaseResponseStatus status;

    public BaseException(BaseResponseStatus status) {
        this.status = status;
    }
}