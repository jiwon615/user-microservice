package com.jimart.userservice.core.security.ajax.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimart.userservice.core.common.ApiResponse;
import com.jimart.userservice.core.exception.ErrorMsgType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static com.jimart.userservice.core.exception.ErrorMsgType.*;

public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // TODO; 추후 Seuciryt 전용 exception 만들어서 AuthenticationException 상속 받게 한 뒤 local msg 출력하도록 리팩토링
        ErrorMsgType errorMsgType = COMMON_SERVER_ERROR;

        if (exception instanceof BadCredentialsException) {
            errorMsgType = AUTH_BAD_CREDENTIAL;
        } else if (exception instanceof DisabledException) {
            errorMsgType = AUTH_DISABLED;
        } else if (exception instanceof CredentialsExpiredException) {
            errorMsgType = AUTH_CREDENTIAL_EXPIRED;
        } else if (exception instanceof UsernameNotFoundException) {
            errorMsgType = AUTH_USERNAME_NOT_FOUND;
        } else if (exception instanceof InsufficientAuthenticationException) {
            errorMsgType = AUTH_EMPTY_USER;
        }

        mapper.writeValue(response.getWriter(), ApiResponse.ofError(errorMsgType));
    }
}
