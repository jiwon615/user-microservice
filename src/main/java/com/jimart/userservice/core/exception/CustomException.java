package com.jimart.userservice.core.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomException extends RuntimeException {

    @Getter
    private ErrorMsgType errorMsgType;

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException(ErrorMsgType errorMsgType, String msg) {
        super(msg);
        this.errorMsgType = errorMsgType;
    }

    public CustomException(ErrorMsgType errorMsgType) {
        super(errorMsgType.getMessage());
        this.errorMsgType = errorMsgType;
    }
}