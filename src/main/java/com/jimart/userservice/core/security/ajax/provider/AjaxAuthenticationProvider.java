package com.jimart.userservice.core.security.ajax.provider;

import com.jimart.userservice.core.security.ajax.service.UserContext;
import com.jimart.userservice.core.security.ajax.token.AjaxAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public AjaxAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginUserId = authentication.getName();
        String loginPassword = (String) authentication.getCredentials();

        UserContext userContext = (UserContext) userDetailsService.loadUserByUsername(loginUserId);

        checkPassword(userContext, loginPassword);

        return new AjaxAuthenticationToken(userContext.getUserDto(), null, userContext.getAuthorities());
    }

    private void checkPassword(UserContext userContext, String password) {
        if (!passwordEncoder.matches(password, userContext.getPassword())) {
            throw new BadCredentialsException("유효하지 않은 패스워드 입니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}
