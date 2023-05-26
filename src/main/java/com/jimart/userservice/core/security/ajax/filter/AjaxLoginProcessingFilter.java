package com.jimart.userservice.core.security.ajax.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimart.userservice.core.exception.CustomException;
import com.jimart.userservice.core.security.ajax.token.AjaxAuthenticationToken;
import com.jimart.userservice.domain.user.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            throw new CustomException(AUTH_BAD_REQUEST);
        }

        UserDto userDto = objectMapper.readValue(request.getReader(), UserDto.class);
        checkIfUserIsEmpty(userDto);

        return deliverTokenToAuthenticationManager(userDto);
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    private void checkIfUserIsEmpty(UserDto userDto) {
        if (!StringUtils.hasText(userDto.getUserId()) || !StringUtils.hasText(userDto.getPassword())) {
            throw new CustomException(AUTH_EMPTY_USER);
        }
    }

    private Authentication deliverTokenToAuthenticationManager(UserDto userDto) {
        AjaxAuthenticationToken token = new AjaxAuthenticationToken(userDto.getUserId(), userDto.getPassword());
        return getAuthenticationManager().authenticate(token);
    }
}
