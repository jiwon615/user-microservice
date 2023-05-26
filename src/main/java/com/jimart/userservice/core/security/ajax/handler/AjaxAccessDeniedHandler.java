package com.jimart.userservice.core.security.ajax.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimart.userservice.core.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.jimart.userservice.core.exception.ErrorMsgType.AUTH_BAD_ACCESS;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);

        mapper.writeValue(response.getWriter(), ApiResponse.orError(AUTH_BAD_ACCESS));
    }
}
