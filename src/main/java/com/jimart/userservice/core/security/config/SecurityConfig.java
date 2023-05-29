package com.jimart.userservice.core.security.config;

import com.jimart.userservice.core.security.ajax.common.AjaxLoginAuthenticationEntryPoint;
import com.jimart.userservice.core.security.ajax.common.AjaxLoginConfigurer;
import com.jimart.userservice.core.security.ajax.handler.AjaxAccessDeniedHandler;
import com.jimart.userservice.core.security.ajax.handler.AjaxAuthenticationFailureHandler;
import com.jimart.userservice.core.security.ajax.handler.AjaxAuthenticationSuccessHandler;
import com.jimart.userservice.core.security.ajax.handler.AjaxLogoutSuccessHandler;
import com.jimart.userservice.core.security.ajax.provider.AjaxAuthenticationProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserDetailsService userDetailsService;
    private final Environment env;
    private final String LOGIN_URL = "/api/v1/login";
    private final String LOGOUT_URL = "/api/v1/logout";

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,
                          @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                          Environment env) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.userDetailsService = userDetailsService;
        this.env = env;
    }

    private final String[] WHITE_LIST = new String[]{
            LOGIN_URL,
            LOGOUT_URL,
            "/health_check",
            "/actuator/**",
            "/api/v1/user/sign-up",
            "/api/v1/user/**", // 임시
            "/**", // 임시
    };

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeHttpRequests(authorize -> {
                    try {
                        authorize
                                .requestMatchers(WHITE_LIST).permitAll()
                                .requestMatchers(PathRequest.toH2Console()).permitAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        http
                .logout()
                .logoutUrl(LOGOUT_URL)
                .logoutSuccessHandler(ajaxLogoutSuccessHandler());
        http
                .exceptionHandling()
                .authenticationEntryPoint(ajaxLoginAuthenticationEntryPoint())
                .accessDeniedHandler(ajaxAccessDeniedHandler());

        http.addFilterBefore(characterEncodingFilter(), LogoutFilter.class);

        customConfigurerAjax(http);

        return http.build();
    }

    private void customConfigurerAjax(HttpSecurity http) throws Exception {
        http
                .apply(new AjaxLoginConfigurer<>())
                .setAuthenticationManager(authenticationManager(authenticationConfiguration))
                .successHandlerAjax(ajaxAuthenticationSuccessHandler())
                .failureHandlerAjax(ajaxAuthenticationFailureHandler())
                .loginProcessingUrl(LOGIN_URL);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        ProviderManager authenticationManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(ajaxAuthenticationProvider());
        return authenticationManager;
    }

    @Bean
    public AuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public LogoutSuccessHandler ajaxLogoutSuccessHandler() {
        return new AjaxLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationEntryPoint ajaxLoginAuthenticationEntryPoint() {
        return new AjaxLoginAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler ajaxAccessDeniedHandler() {
        return new AjaxAccessDeniedHandler();
    }

    @Bean
    public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler(env);
    }

    @Bean
    public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding(StandardCharsets.UTF_8.name());
        filter.setForceEncoding(true);
        return filter;
    }
}
