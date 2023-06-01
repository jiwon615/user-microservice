package com.jimart.userservice.core.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorMsgType {

    // common
    COMMON_SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    PROPERTY_NOT_FOUND(NOT_FOUND, "해당 프로퍼티 정보를 찾지 못했습니다."),

    // user
    USER_DUPLICATED(BAD_REQUEST, "이미 존재하는 아이디입니다."),
    USER_NOT_FOUND(BAD_REQUEST, "존재하지 않는 회원입니다."),

    // auth
    AUTH_BAD_REQUEST(BAD_REQUEST, "ajax 요청이 아닙니다."),
    AUTH_BAD_ACCESS(FORBIDDEN, "해당 페이지에 대한 접근 권한이 없습니다."),
    AUTH_EMPTY_USER(NOT_FOUND, "아이디 혹은 비밀번호가 비었습니다."),
    AUTH_UNAUTHORIZED(UNAUTHORIZED, "로그인이 필요한 페이지 입니다."),
    AUTH_BAD_CREDENTIAL(UNAUTHORIZED, "아이디가 존재하지 않거나 비밀번호가 틀렸습니다."),
    AUTH_DISABLED(UNAUTHORIZED, "로그인 인증이 거부되었습니다."),
    AUTH_CREDENTIAL_EXPIRED(UNAUTHORIZED, "로그인 인증이 만료되었습니다."),
    AUTH_USERNAME_NOT_FOUND(UNAUTHORIZED, "해당 회원을 찾을 수 없습니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
