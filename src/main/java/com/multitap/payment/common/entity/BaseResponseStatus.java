package com.multitap.payment.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// https://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml
// following http status code standard from above

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    /**
     * 2XX: Success(성공)
     **/
    SUCCESS(HttpStatus.OK, true, 200, "요청에 성공하였습니다."),

    /**
     * 4XX: Client Error(클라이언트 에러)
     */
    DISALLOWED_ACTION(HttpStatus.BAD_REQUEST, false, 400, "올바르지 않은 행위 요청입니다."),
    WRONG_JWT_TOKEN(HttpStatus.UNAUTHORIZED, false, 401, "다시 로그인 해주세요"),
    NO_SIGN_IN(HttpStatus.UNAUTHORIZED, false, 401, "로그인을 먼저 진행해주세요"),
    NO_ACCESS_AUTHORITY(HttpStatus.FORBIDDEN, false, 403, "접근 권한이 없습니다"),

    /**
     * 5XX: Server Error(서버 에러)
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "Internal server error"),
    SSE_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, false, 503, "알림 전송에 실패하였습니다."),
    REDIS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "Internal Cache system failure"),
    MAIL_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "Mail system failure"),

    /**
     * Service Related Errors
     */

    // token
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, false, 403, "토큰이 유효하지 않습니다."),

    // Users
    DUPLICATED_USER(HttpStatus.CONFLICT, false, 409, "이미 가입된 멤버입니다."),
    FAILED_TO_LOGIN(HttpStatus.UNAUTHORIZED, false, 400, "아이디 또는 패스워드를 다시 확인하세요."),
    DUPLICATED_SOCIAL_USER(HttpStatus.CONFLICT, false, 409, "이미 소셜 연동된 계정입니다."),
    DUPLICATED_SOCIAL_PROVIDER_USER(HttpStatus.CONFLICT, false, 409, "계정에 동일한 플랫폼이 이미 연동되어있습니다"),
    NO_EXIST_USER(HttpStatus.NOT_FOUND, false, 404, "존재하지 않는 멤버 정보입니다."),
    PASSWORD_MATCH_FAILED(HttpStatus.BAD_REQUEST, false, 400, "패스워드를 다시 확인해주세요."),
    NO_SUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, false, 400, "지원하지 않는 플랫폼입니다"),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, false, 409, "이미 사용중인 닉네임입니다."),
    SAME_NICKNAME(HttpStatus.CONFLICT, false, 409, "현재 사용중인 닉네임입니다."),
    INVALID_EMAIL_ADDRESS(HttpStatus.BAD_REQUEST, false, 400, "이메일을 다시 확인해주세요."),


    // Category
    NO_EXIST_CATEGORY(HttpStatus.NOT_FOUND, false, 404, "존재하지 않는 카테고리입니다."),
    DUPLICATED_CATEGORY_NAME(HttpStatus.CONFLICT, false, 409, "이미 존재하는 카테고리 이름입니다."),
    NO_EXIST_PARENT_CATEGORYCODE(HttpStatus.NOT_FOUND, false, 410, "부모 카테고리 코드가 존재하지 않습니다."),
    // Interest (could be Global not found?)
    NO_EXIST_INTEREST(HttpStatus.NOT_FOUND, false, 404, "존재하지 않는 관심사입니다."),

    // KakaoPay
    NO_KAKAOPAY_RESPONSE(HttpStatus.NOT_FOUND, false, 404, "결제 요청 반환 값이 존재하지 않습니다."),
    NO_KAKOPAY_PAYMENT(HttpStatus.NOT_FOUND, false, 404, "결제 정보가 존재하지 않습니다."),
    // payemnt
    POINT_UPDATE_FAILED(HttpStatus.CONFLICT, false, 409, "포인트 보유량 업데이트에 실패하였습니다."),
    PAYMENT_PROCESS_ERROR(HttpStatus.CONFLICT, false, 409, "결제 정보 처리를 실패하였습니다."),
    UNKNOWN_USER_POINT_RESTORE_ERROR(HttpStatus.CONFLICT, false, 409, "유저 포인트 정보 복구를 실패하였습니다."),
    NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, false, 3006, "보유 포인트가 부족합니다."),


    // Shorts
    NO_EXIST_PRODUCT(HttpStatus.NOT_FOUND, false, 404, "존재하지 않는 상품입니다"),
    NO_EXIST_OPTION(HttpStatus.NOT_FOUND, false, 404, "존재하지 않는 옵션입니다"),

    // Comment
    NO_EXIST_COMMENT(HttpStatus.NOT_FOUND, false, 404, "존재하지 않는 댓글입니다"),
    NO_DELETE_COMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 403, "댓글 삭제 권한이 없습니다"),
    NO_DELETE_RE_COMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 403, "대댓글 삭제 권한이 없습니다"),
    NO_EXIST_RE_COMMENT(HttpStatus.NOT_FOUND, false, 404, "존재하지 않는 대댓글입니다"),
    NO_EXIST_PIN_AUTHORITY(HttpStatus.BAD_REQUEST, false, 403, "고정 권한이 없습니다"),

    // Notification
    NO_EXIST_NOTIFICATION_SETTING(HttpStatus.NOT_FOUND, false, 404, "유저의 알림 설정이 존재하지 않습니다."),
    EXIST_NOTIFICATION_SETTING(HttpStatus.BAD_REQUEST, false, 409, "유저의 알림 설정이 이미 존재합니다."),
    NO_EXIST_NOTIFICATION(HttpStatus.NOT_FOUND, false, 410, "존재하지 않는 알림입니다."),
    CANNOT_SHARE(HttpStatus.BAD_REQUEST, false, 451, "공유할 수 없는 유저입니다."),
    ;

    private final HttpStatus httpStatusCode;
    private final boolean isSuccess;
    private final int code;
    private final String message;
}