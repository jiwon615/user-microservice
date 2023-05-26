package com.jimart.userservice.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorMsgType {

    // user
    USER_DUPLICATED(BAD_REQUEST, "이미 존재하는 아이디입니다."),
    USER_NOT_FOUND(BAD_REQUEST, "존재하지 않는 회원입니다."),

    ;
    private final HttpStatus httpStatus;
    private final String message;
}
