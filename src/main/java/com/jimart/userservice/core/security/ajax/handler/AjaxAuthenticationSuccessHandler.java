package com.jimart.userservice.core.security.ajax.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimart.userservice.domain.user.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Environment env;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserDto userDto = (UserDto) authentication.getPrincipal();

        // hide password
        userDto.setPassword(null);

        String token = createToken(authentication);
        sendResponseWithToken(response, userDto, token);
    }

    private void sendResponseWithToken(HttpServletResponse response, UserDto userDto, String token) throws IOException {
        response.addHeader("token", token);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), userDto);
    }

    private String createToken(Authentication authentication) {
        Key key = getSecretKey(env.getProperty("jwt.secret"));
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(Objects.requireNonNull(env.getProperty("jwt.expiration_time")))))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSecretKey(String secretTokenKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretTokenKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
