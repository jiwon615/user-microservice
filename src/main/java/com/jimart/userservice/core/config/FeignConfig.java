package com.jimart.userservice.core.config;

import com.jimart.userservice.core.exception.feign.FeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public FeignErrorDecoder getFeignErrorDecoder() {
        return new FeignErrorDecoder();
    }
}
