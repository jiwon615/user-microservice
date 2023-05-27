package com.jimart.userservice.core.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jimart.userservice.core.exception.ErrorMsgType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

    private int code;
    private HttpStatus status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok() {
        return of(HttpStatus.OK, null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return of(HttpStatus.CREATED, data);
    }

    public static <T> ApiResponse<T> ofError(ErrorMsgType e) {
        return of(e.getHttpStatus(), e.getMessage(), null);
    }

    public static <T> ApiResponse<T> ofError(HttpStatus httpStatus, String errorMessage) {
        return of(httpStatus, errorMessage, null);
    }

    private static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return of(httpStatus, httpStatus.name(), data);
    }

    private static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(httpStatus, message, data);
    }
}