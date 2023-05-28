package com.jimart.userservice.core.security.ajax.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimart.userservice.core.security.ajax.token.AjaxAuthenticationToken;
import com.jimart.userservice.domain.user.dto.LoginReqDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static com.jimart.userservice.core.exception.ErrorMsgType.AUTH_BAD_REQUEST;
import static com.jimart.userservice.core.exception.ErrorMsgType.AUTH_EMPTY_USER;
import static org.springframework.http.HttpMethod.POST;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login", POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (!isAjax(request)) {
            throw new InsufficientAuthenticationException(AUTH_BAD_REQUEST.getMessage());
        }

        LoginReqDto loginReq = objectMapper.readValue(request.getReader(), LoginReqDto.class);
        checkIfUserIsEmpty(loginReq);

        return deliverTokenToAuthenticationManager(loginReq);
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    private void checkIfUserIsEmpty(LoginReqDto loginReq) {
        if (!StringUtils.hasText(loginReq.getUserId()) || !StringUtils.hasText(loginReq.getPassword())) {
            throw new InsufficientAuthenticationException(AUTH_EMPTY_USER.getMessage());
        }
    }

    private Authentication deliverTokenToAuthenticationManager(LoginReqDto loginReq) {
        AjaxAuthenticationToken token = new AjaxAuthenticationToken(loginReq.getUserId(), loginReq.getPassword());
        return getAuthenticationManager().authenticate(token);
    }
}
