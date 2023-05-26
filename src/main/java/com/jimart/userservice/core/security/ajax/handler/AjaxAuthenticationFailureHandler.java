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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

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

        ErrorMsgType errorMsgType = COMMON_SERVER_ERROR;

        if (exception instanceof BadCredentialsException) {
            errorMsgType = AUTH_BAD_CREDENTIAL;
        } else if (exception instanceof DisabledException) {
            errorMsgType = AUTH_DISABLED;
        } else if (exception instanceof CredentialsExpiredException) {
            errorMsgType = AUTH_CREDENTIAL_EXPIRED;
        } else if (exception instanceof UsernameNotFoundException) {
            errorMsgType = AUTH_USERNAME_NOT_FOUND;
        }

        mapper.writeValue(response.getWriter(), ApiResponse.orError(errorMsgType));
    }
}
