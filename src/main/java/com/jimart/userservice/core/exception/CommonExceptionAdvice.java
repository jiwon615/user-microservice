package com.jimart.userservice.core.exception;

import com.jimart.userservice.core.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class CommonExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e) {
        return ApiResponse.orError(
                HttpStatus.BAD_REQUEST,
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );
    }

    @ExceptionHandler(value = {CustomException.class})
    public ApiResponse<Object> customException(CustomException e) {
        return ApiResponse.orError(
                e.getErrorMsgType().getHttpStatus(),
                e.getMessage()
        );
    }

    @ExceptionHandler(value = {Exception.class})
    public ApiResponse<Object> commonException(Exception e) {
        return ApiResponse.orError(
                INTERNAL_SERVER_ERROR,
                "서버 에러"
        );
    }
}
