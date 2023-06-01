package com.jimart.userservice.core.exception.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.web.server.ResponseStatusException;

import static com.jimart.userservice.core.exception.ErrorMsgType.*;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        int statusCode = response.status();
        if (statusCode == 400) {
            return new ResponseStatusException(COMMON_ERROR_400.getHttpStatus(), COMMON_ERROR_400.getMessage());
        } else if (statusCode == 404) {
            return new ResponseStatusException(COMMON_ERROR_404.getHttpStatus(), COMMON_ERROR_404.getMessage());
        }
        return new Exception(response.reason());
    }
}
