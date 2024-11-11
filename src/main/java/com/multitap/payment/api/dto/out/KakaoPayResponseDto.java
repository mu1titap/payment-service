package com.multitap.payment.api.dto.out;

import java.util.Date;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoPayResponseDto {
    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // web - 받는 결제 페이지
    private Date created_at;

}
